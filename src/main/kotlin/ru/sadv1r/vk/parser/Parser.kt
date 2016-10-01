package ru.sadv1r.vk.parser

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.slf4j.LoggerFactory
import ru.sadv1r.vk.parser.Parser.ResolveScreenNameResult.Type.*
import ru.sadv1r.vk.parser.exceptions.AccessDeniedException
import ru.sadv1r.vk.parser.exceptions.VkException
import ru.sadv1r.vk.parser.exceptions.WrongScreenNameException
import ru.sadv1r.vk.parser.model.Error
import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.InputStreamReader
import java.net.URL
import java.nio.charset.Charset
import javax.net.ssl.HttpsURLConnection

/**
 * Базовый класс для всех парсеров Вконтакте.
 *
 * @param accessToken ключ доступа.
 * @author [sadv1r](http://sadv1r.ru)
 */
abstract class Parser(val accessToken: String? = null) {
    private val logger = LoggerFactory.getLogger(Parser::class.java)
    val baseApiUrl: String = "https://api.vk.com/method/"
    val version: String = "5.24"
    val lang: String = "ru"

    /**
     * Создает шаблон URL адреса для доступа к API Вконтакте.
     *
     * @param method название метода API Вконтакте.
     * @param args аргументы для запроса к методу API Вконтакте.
     * @return шаблон URL адреса.
     */
    //TODO возможно стоит добавить @JvmOverloads
    fun apiUrlTemplate(method: String, args: String = ""): String {
        val apiUrl = "$baseApiUrl$method?v=$version&lang=$lang$args${
        if (accessToken != null) "&access_token=$accessToken" else ""}"

        logger.trace("Сформированный URL: {}", apiUrl)

        return apiUrl
    } //TODO Заменить на URL()

    /**
     * Получает дерево с ответом API Вконтакте.
     *
     * @param method название метода API Вконтакте.
     * @param args аргументы для запроса к методу API Вконтакте.
     * @return [JsonNode] с деревом ответа.
     */
    fun getResponseTree(method: String, args: String = ""): JsonNode {
        logger.trace("Запуск метода getResponseTree(String, String)")

        val apiUrlString = apiUrlTemplate(method, args)
        val apiUrl = URL(apiUrlString)
        val responseTree = jacksonObjectMapper().readTree(apiUrl)
        logger.trace("Ответ от VK API получен: {}", responseTree)

        if (responseTree.has("error")) {
            errorHandler(responseTree)
        }

        return responseTree
    }

    /**
     * Получает дерево с ответом API Вконтакте.
     *
     * @param method название метода API Вконтакте.
     * @param args аргументы для запроса к методу API Вконтакте.
     * @return [JsonNode] с деревом ответа.
     */
    fun getResponseTree(method: String, args: Map<String, Any?>): JsonNode {
        logger.trace("Запуск метода getResponseTree(String, Map<String, Any?>)")

        fun paramGen(map: Map<String, Any?>): String = map
                .filterValues { it != null }
                .asSequence()
                .joinToString("") { "&${it.key}=${it.value}" }

        return getResponseTree(method, paramGen(args))
    }

    /**
     * Получает дерево с ответом API Вконтакте.
     *
     * @param code код алгоритма в **VKScript** - формате, похожем на **JavaSсript** или **ActionScript**
     * (предполагается совместимость с **ECMAScript**).
     * Алгоритм должен завершаться командой **return** **%выражение%**.
     * Операторы должны быть разделены точкой с запятой.
     * @return [JsonNode] с деревом ответа.
     */
    fun getExecuteResponseTree(code: String): JsonNode {
        logger.trace("Запуск метода getExecuteResponseTree(String)")

        val conn = URL(apiUrlTemplate("execute")).openConnection() as HttpsURLConnection
        conn.requestMethod = "POST"
        conn.doOutput = true

        val dataOutputStream = DataOutputStream(conn.outputStream)
        val arguments: ByteArray = "&code=$code".toByteArray(Charset.defaultCharset())
        dataOutputStream.write(arguments)

        val bufferedReader = BufferedReader(InputStreamReader(conn.inputStream))

        val responseTree = jacksonObjectMapper().readTree(bufferedReader)

        if (responseTree.has("error")) {
            errorHandler(responseTree)
        }

        return responseTree
    }

    /**
     * Обработчик ошибок Вконтакте.
     * Бросает исключение, соответствующее ошибке Вконтакте.
     *
     * @param responseTree дерево ответа Вконтакте, содержащее ошибку.
     * @throws VkException исключение на основе ошибки Вконтакте.
     */
    fun errorHandler(responseTree: JsonNode) {
        logger.trace("Запуск метода errorHandler(Error)")

        val error: Error = jacksonObjectMapper().readValue(responseTree.get("error").toString())

        if (error.errorCode == 15) {
            val vkException = AccessDeniedException(error)
            logger.warn("Обнаружена ошибка", vkException)
            throw vkException
        } else if (error.errorCode == 113) {
            val vkException = WrongScreenNameException(error)
            logger.warn("Обнаружена ошибка", vkException)
            throw vkException
        } else {
            val vkException = VkException(error)
            logger.error("Обнаружена неизвестная ошибка", vkException)
            throw VkException(error)
        }
    }

    //TODO ВОЗМОЖНО необходимо перенести в отдельный "UtilParser"
    /**
     * Модель типа объекта (пользователь, сообщество, приложение)
     * и его идентификатора по короткому имени **screen_name**.
     *
     * @param type тип объекта.
     * @param objectId идентификатор объекта.
     */
    data class ResolveScreenNameResult(val type: Type,
                                       @JsonProperty("object_id")
                                       val objectId: Int) {
        /**
         * @property USER пользователь.
         * @property GROUP сообщество.
         * @property APPLICATION приложение.
         * @property PAGE страница.
         */
        enum class Type {
            @JsonProperty("user")
            USER,
            @JsonProperty("group")
            GROUP,
            @JsonProperty("application")
            APPLICATION,
            @JsonProperty("page")
            PAGE
        }
    }
    /**
     * Определяет тип объекта (пользователь, сообщество, приложение)
     * и его идентификатор по короткому имени **screen_name**.
     *
     * @param screenName Короткое имя пользователя, группы или приложения.
     * @return [ResolveScreenNameResult] с типом и идентификатором объекта.
     * @throws WrongScreenNameException если был передан неверный идентификатор пользователя.
     */
    fun resolveScreenName(screenName: String): ResolveScreenNameResult {
        logger.trace("Запуск метода resolveScreenName(String)")

        val methodName = "utils.resolveScreenName"
        logger.trace("Получаем id объекта \"{}\"", screenName)

        val jsonNode = getResponseTree(methodName, "&screen_name=$screenName")
        val result: ResolveScreenNameResult = jacksonObjectMapper()
                .readValue(jsonNode.get("response").toString())
        logger.debug("Получен объект \"{}\" с id: {}", result.type, result.objectId)

        return ResolveScreenNameResult(result.type, result.objectId)
    }
}