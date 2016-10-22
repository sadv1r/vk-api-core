package ru.sadv1r.vk.api.core

//import org.junit.runner.RunWith
//import org.mockito.Mockito
//import org.powermock.api.mockito.PowerMockito
//import org.powermock.core.classloader.annotations.PrepareForTest
//import org.powermock.modules.junit4.PowerMockRunner
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
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

    @Test
    fun getFriends() {
        val expected: List<Int> = listOf(2, 5)

        val getFriendsResult = friendsParser.getFriends(1, count = 2)

        assertEquals(expected, getFriendsResult)
    }

    @Test
    fun test() {
        val k = object : Parser() {}
        val n: Int = jacksonObjectMapper().readValue(k.getResponseTree("friends.get", "&user_id=1").at("/response/count").toString())

        println(n)


    }
}