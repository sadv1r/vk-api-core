package ru.sadv1r.vk.parser

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.apache.log4j.Logger
import ru.sadv1r.vk.parser.model.Album
import ru.sadv1r.vk.parser.model.Photo
import ru.sadv1r.vk.parser.model.Profile

/**
 * Парсер профилей пользователей Вконтакте
 *
 * Created on 4/3/16.
 *
 * @author sadv1r
 * @version 0.1
 */
class ProfileParser: Parser() {
    val logger = Logger.getLogger("ru.sadv1r.vk.monitoring")

    /**
     * Получает профиль пользователя
     *
     * @param vkId id пользователя Вконтакте
     * @return профиль пользователя
     */
    fun getProfile(vkId: Int): Profile {
        val methodName = "users.get"

        val responseTree = getResponseTree(methodName, "&user_ids=$vkId")

        //return getProfile(responseTree)
        return getProfiles(responseTree).first()
    }

    /**
     * @deprecated метод [getProfile] теперь обращается к [getProfiles] и берет первое значение
     * @param jsonNode
     *        {@code JsonNode} с деревом ответа метода Вконтакте [users.get][getProfile]
     * @return профиль пользователя
     */
    private fun getProfile(jsonNode: JsonNode): Profile {
        val result: Profile = jacksonObjectMapper()
                .readValue(jsonNode.get("response").get(0).toString())

        logger.trace("Получен профиль: $result")

        return result
    }

    /**
     * Получает профили пользователей
     *
     * @param vkId id пользователя Вконтакте
     * @return {@code List} профилей пользователей
     */
    fun getProfile(vkId: Array<Int>): List<Profile> {
        val methodName = "users.get"

        val responseTree = getResponseTree(methodName, "&user_ids=${vkId.joinToString(",")}")

        return getProfiles(responseTree)
    }

    /**
     * @param jsonNode
     *        {@code JsonNode} с деревом ответа метода Вконтакте [users.get][getProfile]
     * @return {@code List} профилей пользователей
     */
    private fun getProfiles(jsonNode: JsonNode): List<Profile> {
        val result: List<Profile> = jacksonObjectMapper()
                .readValue(jsonNode.get("response").toString())

        logger.trace("Получен профиль: $result")

        return result
    }
}