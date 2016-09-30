package ru.sadv1r.vk.parser

import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import ru.sadv1r.vk.parser.exceptions.AccessDeniedException
import ru.sadv1r.vk.parser.exceptions.WrongScreenNameException
import ru.sadv1r.vk.parser.model.Error
import java.util.*

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
    fun resolveScreenName() {
        val expectedType = Parser.ResolveScreenNameResult.Type.USER
        val expectedId = 9313032

        val resolveScreenNameResult: Parser.ResolveScreenNameResult = parser.resolveScreenName("sadv1r")

        Assert.assertEquals("Тип объекта определен неверно", expectedType, resolveScreenNameResult.type)
        Assert.assertEquals("Идентификатор объекта определен неверно", expectedId, resolveScreenNameResult.objectId)
    }

//    //FIXME! Тест не проверяет сообщение ошибки. Проблема с ExpectedException Rule (возможно, ExpectedException не понимает Kotlin класс)
//    @Test(expected = AccessDeniedException::class)
//    fun errorHandler() {
//        val params = ArrayList<Error.Param>()
//        params.add(Error.Param("oauth", "1"))
//        params.add(Error.Param("method", "photos.getAlbums"))
//        params.add(Error.Param("parameter1", "value1"))
//        params.add(Error.Param("parameter2", "value2"))
//        val error = Error(15, "Access denied: user is deactivated", params)
//
//        parser.errorHandler(error)
//    }
//
//    //FIXME! Тест не проверяет сообщение ошибки. Проблема с ExpectedException Rule (возможно, ExpectedException не понимает Kotlin класс)
//    @Test(expected = WrongScreenNameException::class)
//    fun errorHandler1() {
//        val params = ArrayList<Error.Param>()
//        params.add(Error.Param("method", "utils.resolveScreenName"))
//        params.add(Error.Param("parameter1", "value1"))
//        params.add(Error.Param("parameter2", "value2"))
//        val error = Error(113, "Error text", params)
//
//        parser.errorHandler(error)
//    }
}