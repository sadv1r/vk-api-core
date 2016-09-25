package ru.sadv1r.vk.parser

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.slf4j.LoggerFactory
import ru.sadv1r.vk.parser.ProfileParser.NameCase.*
import ru.sadv1r.vk.parser.model.Execute
import ru.sadv1r.vk.parser.model.Profile

/**
 * Парсер профилей пользователей Вконтакте
 *
 * Created on 4/3/16.
 *
 * @author sadv1r
 * @version 0.1
 */
class ProfileParser(accessToken: String? = null) : Parser(accessToken) {
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
    fun getProfile(vkId: List<Int>, fields: String? = null, nameCase: NameCase? = null): List<Profile> {
        val friendsListSize = vkId.size
        var maximumFriendsOnRequest = 1000

        if (friendsListSize > maximumFriendsOnRequest && accessToken != null) {
            /*val params = mapOf("fields" to fields, "name_case" to nameCase)

            val code: String = vkId.asSequence().batch(maximumFriendsOnRequest)
                    .joinToString("%2b", "return ", ";") { batch ->
                        params.filterValues { it != null }
                                .asSequence()
                                .joinToString(",", "API.users.get({user_ids:$batch,", "})") { param ->
                                    "${param.key}:\"${param.value}\""
                                }
                    }*/

            val execute = Execute()
            val params = mutableMapOf("fields" to fields, "name_case" to nameCase)

            vkId.asSequence().batch(maximumFriendsOnRequest).forEach {
                params.put("user_ids", it)
                execute.append("users.get", params)
            }

            val code = execute.compose()

            val responseTree = getExecuteResponseTree(code)

            return getProfiles(responseTree)
        } else {
            val methodName = "users.get"

            maximumFriendsOnRequest = 400
            val result: MutableList<Profile> = mutableListOf()

            for (offset in 0..friendsListSize step maximumFriendsOnRequest) {       //FIXME Заменить на Do While
                val responseTree = getResponseTree(methodName, "&fields=$fields&user_ids=${
                vkId.subList(offset, if (friendsListSize > offset + maximumFriendsOnRequest)
                    offset + maximumFriendsOnRequest else friendsListSize).joinToString(",")}")
                result.addAll(getProfiles(responseTree))
            }
            return result
        }
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