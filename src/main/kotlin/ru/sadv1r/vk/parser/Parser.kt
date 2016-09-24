package ru.sadv1r.vk.parser

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.slf4j.LoggerFactory
import ru.sadv1r.vk.parser.exceptions.AccessDeniedException
import ru.sadv1r.vk.parser.exceptions.VkException
import ru.sadv1r.vk.parser.exceptions.WrongScreenNameException
import ru.sadv1r.vk.parser.model.Error
import java.net.URL

/**
 * Базовый класс для всех парсеров Вконтакте
 *
 * Created on 4/3/16.
 *
 * @author sadv1r
 * @version 0.1
 */
abstract class Parser {
    private val logger = LoggerFactory.getLogger(Parser::class.java)
    val baseApiUrl: String = "https://api.vk.com/method/"
    val version: String = "5.24"
    val lang: String = "ru"

    /**
     * Создает шаблон URL адреса для доступа к API Вконтакте
     *
     * @param method название метода API Вконтакте
     * @param args аргументы для запроса к методу API Вконтакте
     * @return шаблон URL адреса
     */
    fun apiUrlTemplate(method: String, args: String = ""): String {
        return "$baseApiUrl$method?v=$version&lang=$lang$args"
    }

    /**
     * Получает дерево с ответом API Вконтакте
     *
     * @param method название метода API Вконтакте
     * @param args аргументы для запроса к методу API Вконтакте
     * @return {@code JsonNode} с деревом ответа
     */
    fun getResponseTree(method: String, args: String = ""): JsonNode {
        val apiUrlString = apiUrlTemplate(method, args)
        val apiUrl = URL(apiUrlString)
        val responseTree = jacksonObjectMapper().readTree(apiUrl)
        logger.trace("Ответ от VK API получен")

        if (responseTree.has("error")) {
            val error: Error = jacksonObjectMapper().readValue(responseTree.get("error").toString())
            errorHandler(error)
        }

        return responseTree
    }

    /**
     * Получает дерево с ответом API Вконтакте
     *
     * @param method название метода API Вконтакте
     * @param args аргументы для запроса к методу API Вконтакте
     * @return {@code JsonNode} с деревом ответа
     */
    fun getResponseTree(method: String, args: Map<String, Any?>): JsonNode {
        fun paramGen(map: Map<String, Any?>): String = map
                .filterValues { it != null }
                .asSequence()
                .joinToString("") { "&${it.key}=${it.value}" }

        val apiUrlString = apiUrlTemplate(method, paramGen(args))
        val apiUrl = URL(apiUrlString)
        val responseTree = jacksonObjectMapper().readTree(apiUrl)
        logger.trace("Ответ от VK API получен")

        if (responseTree.has("error")) {
            val error: Error = jacksonObjectMapper().readValue(responseTree.get("error").toString())
            errorHandler(error)
        }

        return responseTree
    }

    /**
     * Обработчик ошибок Вконтакте
     *
     * Бросает исключение, соответствующее ошибке Вконтакте
     *
     * @param error имплементация ошибки Вконтакте
     * @throws VkException
     */
    fun errorHandler(error: Error) {
        if (error.errorCode == 15)
            throw AccessDeniedException(error.errorMsg)
        if (error.errorCode == 113)
            throw WrongScreenNameException(error.errorMsg)
        else
            throw VkException()
    }

    //TODO ВОЗМОЖНО необходимо перенести в отдельный "UtilParser"
    /**
     * Модель типа объекта (пользователь, сообщество, приложение)
     * и его идентификатора по короткому имени screen_name
     *
     * @param type тип объекта
     * @param objectId идентификатор объекта
     */
    data class ResolveScreenNameResult(val type: Type,
                                       @JsonProperty("object_id")
                                       val objectId: Int) {
        /**
         * @property USER пользователь
         * @property GROUP сообщество
         * @property APPLICATION приложение
         * @property PAGE страница
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
     * @param screenName Короткое имя пользователя, группы или приложения
     * @return {@code ResolveScreenNameResult} с типом и идентификатором объекта
     * @throws IllegalArgumentException
     */
    fun resolveScreenName(screenName: String): ResolveScreenNameResult {
        logger.trace("Запуск метода resolveScreenName(String)")
        val methodName = "utils.resolveScreenName"
        logger.trace("Получаем id объекта \"" + screenName + "\"")

        val jsonNode = getResponseTree(methodName, "&screen_name=$screenName")
        val result: ResolveScreenNameResult = jacksonObjectMapper()
                .readValue(jsonNode.get("response").toString())
        logger.debug("Получен объект \"${result.type}\" с id: ${result.objectId}")

        return ResolveScreenNameResult(result.type, result.objectId)
    }
}