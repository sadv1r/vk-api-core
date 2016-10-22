package ru.sadv1r.vk.api.core

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.junit.*
import org.junit.Assert.*
import org.junit.Assume.*
import ru.sadv1r.vk.api.core.exceptions.VkException
import java.net.URL

/**
 * Created on 4/4/16.
 *
 * @author sadv1r
 *
 * @version 0.1
 */
class ParserTest {
    lateinit var parser: Parser

    @Before
    fun setUp() {
        parser = object : Parser() {}
    }

    @After
    fun tearDown() {

    }

    @Test
    fun apiUrlTemplate() {
        val expected = URL("https://api.vk.com/method/utils.getServerTime?v=5.24&lang=ru")

        val actual: URL = parser.apiUrlTemplate("utils.getServerTime")

        assertEquals("URL без параметров составлен неверно", expected, actual)
    }

    @Test
    fun apiUrlTemplate1() {
        val expected = URL("https://api.vk.com/method/utils.getServerTime?v=5.24&lang=ru" +
                "&parameter1=value1&parameter2=value2")

        val actual: URL = parser.apiUrlTemplate("utils.getServerTime",
                "&parameter1=value1&parameter2=value2")

        assertEquals("URL с параметрами составлен неверно", expected, actual)
    }

    @Test
    fun getResponseTree() {
        val actual: JsonNode = parser.getResponseTree("utils.getServerTime")

        assertTrue("Получено неверное дерево с ответом на запрос без параметров", actual.get("response").isInt)
    }

    @Test
    fun getResponseTree1() {
        val actual = parser.getResponseTree("utils.resolveScreenName", "&screen_name=sadv1r")
                .get("response")

        assertTrue("Получено неверное дерево с ответом на запрос с параметрами", actual.get("type").isTextual)
        assertTrue("Получено неверное дерево с ответом на запрос с параметрами", actual.get("object_id").isInt)
    }

    @Test
    fun getResponseTree3() {
        val params = mapOf("screen_name" to "sadv1r")
        val actual = parser.getResponseTree("utils.resolveScreenName", params)
                .get("response")

        assertTrue("Получено неверное дерево с ответом на запрос с параметрами в мапе", actual.get("type").isTextual)
        assertTrue("Получено неверное дерево с ответом на запрос с параметрами в мапе", actual.get("object_id").isInt)
    }

    @Test
    fun resolveScreenName() {
        val expectedType = Parser.ResolveScreenNameResult.Type.USER
        val expectedId = 9313032

        val resolveScreenNameResult: Parser.ResolveScreenNameResult = parser.resolveScreenName("sadv1r")

        assertEquals("Тип объекта определен неверно", expectedType, resolveScreenNameResult.type)
        assertEquals("Идентификатор объекта определен неверно", expectedId, resolveScreenNameResult.objectId)
    }

    //FIXME! Тест не проверяет сообщение ошибки. Проблема с ExpectedException Rule (возможно, ExpectedException не понимает Kotlin класс)
    @Test(expected = VkException::class)
    fun errorHandler() {
        val node: JsonNode = jacksonObjectMapper().readTree("{\"error\":" +
                "{\"error_code\":100500,\"error_msg\":\"Test error\",\"request_params\":[" +
                "{\"key\":\"oauth\",\"value\":\"1\"}," +
                "{\"key\":\"method\",\"value\":\"api.method\"}," +
                "{\"key\":\"user_id\",\"value\":\"sadv1r\"}" +
                "]}" +
                "}")

        parser.errorHandler(node)
    }

    @Test
    fun errorHandlerAccessDeniedException() {
        val nodes = mutableMapOf<String, JsonNode>()
        val accessDeniedExceptionNode: JsonNode = jacksonObjectMapper().readTree("{\"error\":" +
                "{\"error_code\":15,\"error_msg\":\"Test error\",\"request_params\":[" +
                "{\"key\":\"oauth\",\"value\":\"1\"}," +
                "{\"key\":\"method\",\"value\":\"api.method\"}," +
                "{\"key\":\"user_id\",\"value\":\"sadv1r\"}" +
                "]}" +
                "}")
        val wrongScreenNameException: JsonNode = jacksonObjectMapper().readTree("{\"error\":" +
                "{\"error_code\":113,\"error_msg\":\"Test error\",\"request_params\":[" +
                "{\"key\":\"oauth\",\"value\":\"1\"}," +
                "{\"key\":\"method\",\"value\":\"api.method\"}," +
                "{\"key\":\"user_id\",\"value\":\"sadv1r\"}" +
                "]}" +
                "}")
        nodes.put("AccessDeniedException", accessDeniedExceptionNode)
        nodes.put("WrongScreenNameException", wrongScreenNameException)

        for ((key, value) in nodes) {
            try {
                parser.errorHandler(value)
            } catch (e: VkException) {
                assertEquals(key, e.javaClass.simpleName)
            }
        }
    }
}