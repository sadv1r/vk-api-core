package ru.sadv1r.vk.parser

import org.junit.After
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
        println(parser.resolveScreenName("sadv1r"))
    }
}