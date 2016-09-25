package ru.sadv1r.vk.parser

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.slf4j.LoggerFactory
import ru.sadv1r.vk.parser.model.Profile

/**
 * Парсер профилей пользователей Вконтакте
 *
 * Created on 4/3/16.
 *
 * @author sadv1r
 * @version 0.1
 */
class ProfileParser : Parser() {
    private val logger = LoggerFactory.getLogger(ProfileParser::class.java)

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
    @Deprecated("Функция устарела", ReplaceWith("getProfiles(jsonNode).first()"))
    private fun getProfile(jsonNode: JsonNode): Profile {
        val result: Profile = jacksonObjectMapper()
                .readValue(jsonNode.get("response").get(0).toString())

        logger.trace("Получен профиль: {}", result)

        return result
    }

    /**
     * Получает профили пользователей
     *
     * @param vkId id пользователя Вконтакте
     * @return {@code List} профилей пользователей
     */
    fun getProfile(vkId: List<Int>, fields: String = ""): List<Profile> {
        val methodName = "users.get"
        val maximumFriendsOnRequest = 400
        val result: MutableList<Profile> = mutableListOf()

        val friendsListSize = vkId.size
        for (offset in 0..friendsListSize step maximumFriendsOnRequest) {
            val responseTree = getResponseTree(methodName, "&fields=$fields&user_ids=${
            vkId.subList(offset, if (friendsListSize > offset + maximumFriendsOnRequest)
                offset + maximumFriendsOnRequest else friendsListSize).joinToString(",")}")
            result.addAll(getProfiles(responseTree))
        }

        return result
    }

    /**
     * @param jsonNode
     *        {@code JsonNode} с деревом ответа метода Вконтакте [users.get][getProfile]
     * @return {@code List} профилей пользователей
     */
    private fun getProfiles(jsonNode: JsonNode): List<Profile> {
        val result: List<Profile> = jacksonObjectMapper()
                .readValue(jsonNode.get("response").toString())

        logger.trace("Получен профиль: {}", result)

        return result
    }
}