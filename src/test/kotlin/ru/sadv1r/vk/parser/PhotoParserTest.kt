package ru.sadv1r.vk.parser

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.junit.After
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
//import org.junit.runner.RunWith
//import org.mockito.Mockito
//import org.powermock.api.mockito.PowerMockito
//import org.powermock.core.classloader.annotations.PrepareForTest
//import org.powermock.modules.junit4.PowerMockRunner
import ru.sadv1r.vk.parser.model.Error
import ru.sadv1r.vk.parser.exceptions.AccessDeniedException
import ru.sadv1r.vk.parser.model.Album
import ru.sadv1r.vk.parser.model.Photo
import java.sql.Timestamp
import java.util.*
import kotlin.reflect.functions
import kotlin.reflect.jvm.isAccessible

/**
 * Created on 4/4/16.
 *
 * @author sadv1r
 *
 * @version 0.1
 */
//@RunWith(PowerMockRunner::class)
//@PrepareForTest(PhotoParser::class)
class PhotoParserTest {
    lateinit var photoParser: PhotoParser

    @Before
    fun setUp() {
        photoParser = PhotoParser()
    }

    @After
    fun tearDown() {

    }

    @Test
    fun getAlbums() {
        val node: JsonNode = jacksonObjectMapper().readTree("{\"response\":{\"count\":2,\"items\":[" +
                "{\"id\":207791859,\"thumb_id\":0,\"owner_id\":1,\"title\":\"Instagram\",\"description\":\"\",\"created\":1418078972,\"updated\":1418078972,\"size\":0,\"thumb_is_last\":1}," +
                "{\"id\":136592355,\"thumb_id\":348815410,\"owner_id\":1,\"title\":\"Здесь будут новые фотографии для прессы-службы\",\"description\":\"\",\"created\":1307628778,\"updated\":1418772499,\"size\":9,\"thumb_is_last\":1}" +
                "]}}")
        val expected = ArrayList<Album>()
        expected.add(Album(207791859, 1, "Instagram", Timestamp(1418078972000), Timestamp(1418078972000), 0))
        expected.add(Album(136592355, 1, "Здесь будут новые фотографии для прессы-службы", Timestamp(1307628778000), Timestamp(1418772499000), 9))

/*TODO! Исправить, когда в Kotlin появится поддержка PowerMockito или аналог. Пока тестируем через Reflection
        val mockRealm = PowerMockito.mock(PhotoParser::class.java)

        Mockito.`when`(mockRealm.getResponseTree("photos.getAlbums", "&owner_id=1")).thenReturn(node)
        val actual = mockRealm.getAlbums(1)

        assertEquals(expected, actual)
*/

/*Часть, необходимая для отладки Reflection
        PhotoParser::class.functions.find {
            it.name.equals("getAlbums") && it.parameters[1].name.equals("jsonNode")
        }!!.parameters.forEach {
            println(it.type)
        }
*/

        val methodGetAlbums = PhotoParser::class.functions.single() {
            it.name.equals("getAlbums") && it.parameters[1].name.equals("jsonNode")
        }

        val methodGetAlbumsIsAccessible = methodGetAlbums.isAccessible

        methodGetAlbums.isAccessible = true
        @Suppress("UNCHECKED_CAST")
        val getAlbumsResult = methodGetAlbums.call(photoParser, node) as List<Album>
        methodGetAlbums.isAccessible = methodGetAlbumsIsAccessible

        for ((index, value) in expected.withIndex()) {
            assertEquals(value.id, getAlbumsResult[index].id)
            assertEquals(value.ownerId, getAlbumsResult[index].ownerId)
            assertEquals(value.title, getAlbumsResult[index].title)
            assertEquals(value.created, getAlbumsResult[index].created)
            assertEquals(value.updated, getAlbumsResult[index].updated)
            assertEquals(value.size, getAlbumsResult[index].size)
        }
    }

    @Test
    fun getPhotos() {
        val node: JsonNode = jacksonObjectMapper().readTree("{\"response\":{\"count\":2,\"items\":[" +
                "{\"id\":263113261,\"album_id\":136592355,\"owner_id\":1,\"photo_75\":\"http://cs9591.vk.me/u00001/136592355/s_47267f71.jpg\",\"photo_130\":\"http://cs9591.vk.me/u00001/136592355/m_dc54094a.jpg\",\"photo_604\":\"http://cs9591.vk.me/u00001/136592355/x_3216ccc1.jpg\",\"photo_807\":\"http://cs9591.vk.me/u00001/136592355/y_e10ee835.jpg\",\"photo_1280\":\"http://cs9591.vk.me/u00001/136592355/z_a8fd75ba.jpg\",\"photo_2560\":\"http://cs9591.vk.me/u00001/136592355/w_62aef149.jpg\",\"text\":\"Некоторое описание фотографии\",\"date\":1307628890}," +
                "{\"id\":312177624,\"album_id\":136592355,\"owner_id\":1,\"photo_75\":\"http://cs7064.vk.me/c540101/v540101001/4847/TheKsCcSV0A.jpg\",\"photo_130\":\"http://cs7064.vk.me/c540101/v540101001/4848/n8L9XeNpicE.jpg\",\"photo_604\":\"http://cs7064.vk.me/c540101/v540101001/4849/Mc63nZlMkyw.jpg\",\"photo_807\":\"http://cs7064.vk.me/c540101/v540101001/484a/-zTV-Vf4ibY.jpg\",\"photo_1280\":\"http://cs7064.vk.me/c540101/v540101001/484b/49OlZbdrjsk.jpg\",\"photo_2560\":\"http://cs7064.vk.me/c540101/v540101001/484c/65oW_-qobmE.jpg\",\"width\":1920,\"height\":1080,\"text\":\"\",\"date\":1380289479}" +
                "]}}")
        val expected = ArrayList<Photo>()
        expected.add(Photo(263113261, 136592355, 1, 1, "Некоторое описание фотографии", Timestamp(1307628890000)))
        expected.add(Photo(312177624, 136592355, 1, 1, "", Timestamp(1380289479000)))

        val methodGetPhotos = PhotoParser::class.functions.single() {
            it.name.equals("getPhotos") && it.parameters[1].name.equals("jsonNode")
        }

        val methodGetPhotosIsAccessible = methodGetPhotos.isAccessible

        methodGetPhotos.isAccessible = true
        @Suppress("UNCHECKED_CAST")
        val getPhotosResult = methodGetPhotos.call(photoParser, node) as List<Photo>
        methodGetPhotos.isAccessible = methodGetPhotosIsAccessible

        for ((index, value) in expected.withIndex()) {
            assertEquals(value.id, getPhotosResult[index].id)
            assertEquals(value.ownerId, getPhotosResult[index].ownerId)
            assertEquals(value.text, getPhotosResult[index].text)
            assertEquals(value.date, getPhotosResult[index].date)
        }
    }

    @Test
    fun apiUrlTemplate() {
        val expected = "https://api.vk.com/method/utils.getServerTime?v=5.24&lang=ru"

        val actual: String = photoParser.apiUrlTemplate("utils.getServerTime")

        assertEquals("URL без параметров составлен не верно", expected, actual)
    }

    @Test
    fun apiUrlTemplate1() {
        val expected = "https://api.vk.com/method/utils.getServerTime?v=5.24&lang=ru&parameter1=value1&parameter2=value2"

        val actual: String = photoParser.apiUrlTemplate("utils.getServerTime", "&parameter1=value1&parameter2=value2")

        assertEquals("URL с параметрами составлен не верно", expected, actual)
    }

    @Test
    fun getResponseTree() {

    }

    @Test
    fun getResponseTree1() {

    }

    //FIXME! Тест не проверяет сообщение ошибки. Проблема с ExpectedException Rule (возможно, ExpectedException не понимает Kotlin класс)
    @Test(expected = AccessDeniedException::class)
    fun errorHandler() {
        val params = ArrayList<Error.Param>()
        params.add(Error.Param("oauth", "1"))
        params.add(Error.Param("method", "photos.getAlbums"))
        params.add(Error.Param("parameter1", "value1"))
        params.add(Error.Param("parameter2", "value2"))
        val error = Error(15, "Access denied: user is deactivated", params)

        photoParser.errorHandler(error)
    }

}