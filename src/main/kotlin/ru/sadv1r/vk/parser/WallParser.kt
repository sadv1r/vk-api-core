package ru.sadv1r.vk.parser

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.apache.log4j.Logger
import ru.sadv1r.vk.parser.model.Post

/**
 * Created on 4/30/16.
 *
 * @author sadv1r
 * @version 0.1
 */
class WallParser: Parser() {
    val logger = Logger.getLogger("ru.sadv1r.vk.monitoring")

    /**
     * Получает посты пользователя
     *
     * @param vkId id пользователя Вконтакте
     * @return {@code List} постов пользователя
     */
    fun getPosts(vkId: Int): List<Post> {
        val methodName = "wall.get"

        val responseTree = getResponseTree(methodName, "&owner_id=$vkId")

        return getPosts(responseTree)
    }

    /**
     * @param jsonNode
     *        {@code JsonNode} с деревом ответа метода Вконтакте {@code wall.get}
     * @return {@code List} постов пользователя
     */
    fun getPosts(jsonNode: JsonNode): List<Post> {
        val result: List<Post> = jacksonObjectMapper()
                .readValue(jsonNode.get("response").get("items").toString())

        var temp = ""; result.forEach { temp += "${it.id} " }
        logger.trace("Полученные посты: $temp")

        return result
    }
}