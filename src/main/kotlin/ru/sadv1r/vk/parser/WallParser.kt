package ru.sadv1r.vk.parser

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.slf4j.LoggerFactory
import ru.sadv1r.vk.parser.model.Post

/**
 * Парсер стен Вконтакте
 *
 * Created on 4/30/16.
 *
 * @author sadv1r
 * @version 0.1
 */
class WallParser: Parser() {
    private val logger = LoggerFactory.getLogger(WallParser::class.java)

    /**
     * Получает посты пользователя
     *
     * @param vkId id пользователя Вконтакте
     * @param offset смещение, необходимое для выборки определенного подмножества записей
     * @param count количество записей, которое необходимо получить (но не более 100)
     * @return {@code List} постов пользователя
     */
    fun getPosts(vkId: Int, offset: Int? = null, count: Int? = null): List<Post> {
        val methodName = "wall.get"

        val responseTree = getResponseTree(methodName, "&owner_id=$vkId${if (offset != null) "&offset=$offset" else "" }${if (count != null) "&count=$count" else "" }") //FIXME просто адская конструкция

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