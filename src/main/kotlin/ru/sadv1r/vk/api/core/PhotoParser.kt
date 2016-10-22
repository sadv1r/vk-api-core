package ru.sadv1r.vk.api.core

import com.fasterxml.jackson.core.JsonPointer
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.slf4j.LoggerFactory
import ru.sadv1r.vk.api.core.model.Album
import ru.sadv1r.vk.api.core.model.Photo

/**
 * Парсер фотографий и альбомов Вконтакте
 *
 * Created on 4/3/16.
 *
 * @author sadv1r
 * @version 0.1
 */
open class PhotoParser: Parser() {
    private val logger = LoggerFactory.getLogger(PhotoParser::class.java)

    /**
     * Получает альбомы пользователя
     *
     * @param vkId id пользователя Вконтакте
     * @return {@code List} альбомов пользователя
     */
    fun getAlbums(vkId: Int): List<Album> {
        val methodName = "photos.getAlbums"

        val responseTree = getResponseTree(methodName, "&owner_id=$vkId")

        return getAlbums(responseTree)
    }

    /**
     * @param jsonNode
     *        {@code JsonNode} с деревом ответа метода Вконтакте {@code photo.getAlbums}
     * @return {@code List} альбомов пользователя
     */
    private fun getAlbums(jsonNode: JsonNode): List<Album> {
        val result: List<Album> = jacksonObjectMapper()
                .readValue(jsonNode.get("response").get("items").toString())

        var temp = ""; result.forEach { temp += "${it.id} " }
        logger.trace("Полученные альбомы: $temp")

        return result
    }
//    private fun getAlbums(jsonNode: JsonNode): List<Album> {
//        val jsonPointer: JsonPointer = JsonPointer.valueOf("/response/items")
//        val result: List<Album> = getParsableVkObjects<Album>(jsonNode, jsonPointer) as List<Album>
//        println(result)
//        return result
//    }

    /**
     * Получает фотографии пользователя
     *
     * @param vkId id пользователя Вконтакте
     * @param albumId id альбома пользователя Вконтакте
     * @return {@code List} фотографий пользователя
     */
    fun getPhotos(vkId: Int, albumId: Int): List<Photo> {
        val methodName = "photos.get"

        val responseTree = getResponseTree(methodName, "&owner_id=$vkId&album_id=$albumId")

        return getPhotos(responseTree)
    }

    /**
     * @param jsonNode
     *        {@code JsonNode} с деревом ответа метода Вконтакте {@code photo.get}
     * @return {@code List} фотографий пользователя
     */
    private fun getPhotos(jsonNode: JsonNode): List<Photo> {
        val result: List<Photo> = jacksonObjectMapper()
                .readValue(jsonNode.get("response").get("items").toString())

        var temp = ""; result.forEach { temp += "${it.id} " }
        logger.trace("Полученные фотографии: $temp")

        return result
    }

}