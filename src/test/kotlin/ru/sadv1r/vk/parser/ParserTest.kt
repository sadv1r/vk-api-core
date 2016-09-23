package ru.sadv1r.vk.parser

import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

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
        Assert.assertEquals("URL без параметров составлен не верно", expectedType, resolveScreenNameResult.type)
        Assert.assertEquals("URL без параметров составлен не верно", expectedId, resolveScreenNameResult.objectId)
    }
}