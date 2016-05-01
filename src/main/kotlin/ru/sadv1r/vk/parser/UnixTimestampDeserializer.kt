package ru.sadv1r.vk.parser

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import java.sql.Timestamp
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * Created on 4/3/16.
 *
 * @author sadv1r
 * @version 0.1
 */
class UnixTimestampDeserializer: JsonDeserializer<Date>() {
    override fun deserialize(parser:JsonParser, context:DeserializationContext):Date {
        val unixTimestamp = parser.text.trim()
        return Timestamp(TimeUnit.SECONDS.toMillis(unixTimestamp.toLong()))
    }
}