package ru.sadv1r.vk.parser

//import org.junit.runner.RunWith
//import org.mockito.Mockito
//import org.powermock.api.mockito.PowerMockito
//import org.powermock.core.classloader.annotations.PrepareForTest
//import org.powermock.modules.junit4.PowerMockRunner
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

/**
 * Created on 4/4/16.
 *
 * @author sadv1r
 *
 * @version 0.1
 */
class FriendsParserTest {
    lateinit var friendsParser: FriendsParser

    @Before
    fun setUp() {
        friendsParser = FriendsParser()
    }

    @After
    fun tearDown() {

    }

    //TODO Переписать на тест через искусственный JsonNode
    @Test
    fun getFriends() {
        val expected: List<Int> = listOf(2, 5)

        val getFriendsResult = friendsParser.getFriends(1, count = 2)

        assertEquals(expected, getFriendsResult)
    }
}