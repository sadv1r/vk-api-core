package ru.sadv1r.vk.api.core

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.slf4j.LoggerFactory
import ru.sadv1r.vk.api.core.ProfileParser.NameCase.*
import ru.sadv1r.vk.api.core.model.Profile

/**
 * Парсер профилей пользователей Вконтакте
 *
 * Created on 4/3/16.
 *
 * @author sadv1r
 * @version 0.1
 */
open class ProfileParser(accessToken: String? = null) : Parser(accessToken) {
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
    open fun getProfile(vkId: List<Int>, fields: String? = null, nameCase: NameCase? = null): List<Profile> {
        val methodName = "users.get"

        val params = mapOf("user_ids" to vkId.joinToString(","), "fields" to fields, "name_case" to nameCase)

        val responseTree = getResponseTree(methodName, params)

        return getProfiles(responseTree)
    }

    /**
     * @param jsonNode
     *        {@code JsonNode} с деревом ответа метода Вконтакте [users.get][getProfile]
     * @return {@code List} профилей пользователей
     */
    fun getProfiles(jsonNode: JsonNode): List<Profile> {
        val result: List<Profile> = jacksonObjectMapper()
                .readValue(jsonNode.get("response").toString())

        logger.trace("Получен профиль: {}", result)

        return result
    }

    /**
     * @property NOM именительный
     * @property GEN родительный
     * @property DAT дательный
     * @property ACC винительный
     * @property INS творительный
     * @property ABL предложный
     */
    enum class NameCase {
        NOM,
        GEN,
        DAT,
        ACC,
        INS,
        ABL;

        override fun toString(): String = this.name.toLowerCase()
    }
}