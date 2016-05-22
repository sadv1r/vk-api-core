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
import ru.sadv1r.vk.parser.model.Profile
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
class ProfileParserTest {
    lateinit var profileParser: ProfileParser

    @Before
    fun setUp() {
        profileParser = ProfileParser()
    }

    @After
    fun tearDown() {

    }

    @Test
    fun getProfile() {
        val node: JsonNode = jacksonObjectMapper().readTree("{\"response\":[" +
                "{\"id\":1,\"first_name\":\"Павел\",\"last_name\":\"Дуров\"}" +
                "]}")
        val expected = Profile(1, "Павел", "Дуров")

        val methodGetProfile = ProfileParser::class.functions.single() {
            it.name.equals("getProfile") && it.parameters[1].name.equals("jsonNode")
        }

        val methodGetProfileIsAccessible = methodGetProfile.isAccessible

        methodGetProfile.isAccessible = true
        val getProfileResult = methodGetProfile.call(profileParser, node) as Profile
        methodGetProfile.isAccessible = methodGetProfileIsAccessible

        assertEquals(expected.id, getProfileResult.id)
        assertEquals(expected.firstName, getProfileResult.firstName)
        assertEquals(expected.lastName, getProfileResult.lastName)
    }

    @Test
    fun getProfile1() {
        val node: JsonNode = jacksonObjectMapper().readTree("{\"response\":[" +
                "{\"id\":1,\"first_name\":\"Павел\",\"last_name\":\"Дуров\",\"sex\":2,\"bdate\":\"10.10.1984\",\"city\":{\"id\":2,\"title\":\"Санкт-Петербург\"},\"country\":{\"id\":1,\"title\":\"Россия\"},\"photo_50\":\"http://cs629231.vk.me/v629231001/c543/FfB--bOEVOY.jpg\",\"photo_id\":\"1_376599151\",\"has_photo\":1,\"verified\":1,\"home_town\":\"\"}" +
                "]}")

        val expected = Profile(1, "Павел", "Дуров", sex = 2, bdate = "10.10.1984", city = Profile.City(2, "Санкт-Петербург"), country = Profile.Country(1, "Россия"), photo50 = "http://cs629231.vk.me/v629231001/c543/FfB--bOEVOY.jpg", photoId = "1_376599151", hasPhoto = true, verified = true, homeTown = "")

        val methodGetProfile = ProfileParser::class.functions.single() {
            it.name.equals("getProfile") && it.parameters[1].name.equals("jsonNode")
        }

        val methodGetProfileIsAccessible = methodGetProfile.isAccessible

        methodGetProfile.isAccessible = true
        val getProfileResult = methodGetProfile.call(profileParser, node) as Profile
        methodGetProfile.isAccessible = methodGetProfileIsAccessible

        assertEquals(expected.id, getProfileResult.id)
        assertEquals(expected.firstName, getProfileResult.firstName)
        assertEquals(expected.sex, getProfileResult.sex)
        assertEquals(expected.bdate, getProfileResult.bdate)
        assertEquals(expected.city, getProfileResult.city)
        assertEquals(expected.country, getProfileResult.country)
        assertEquals(expected.photo50, getProfileResult.photo50)
        assertEquals(expected.photoId, getProfileResult.photoId)
        assertEquals(expected.hasPhoto, getProfileResult.hasPhoto)
        assertEquals(expected.verified, getProfileResult.verified)
        assertEquals(expected.homeTown, getProfileResult.homeTown)
        assertEquals(expected.city!!.id, getProfileResult.city!!.id)
        assertEquals(expected.city!!.title, getProfileResult.city!!.title)
        assertEquals(expected.country!!.id, getProfileResult.country!!.id)
        assertEquals(expected.country!!.title, getProfileResult.country!!.title)
    }
}