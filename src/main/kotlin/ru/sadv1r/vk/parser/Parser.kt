package ru.sadv1r.vk.parser

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import ru.sadv1r.vk.parser.exceptions.AccessDeniedException
import ru.sadv1r.vk.parser.exceptions.VkException
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
        else
            throw VkException()
    }
}