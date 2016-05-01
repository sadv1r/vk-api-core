package ru.sadv1r.vk.parser.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import ru.sadv1r.vk.parser.UnixTimestampDeserializer
import java.sql.Timestamp

/**
 * Модель альбома Вконтакте
 *
 * @author sadv1r
 * @version 0.1
 * @since 0.1
 *
 * @property id идентификатор альбома
 * @property ownerId идентификатор владельца альбома
 * @property title название альбома
 * @property created
 *           Дата создания альбома в формате <b>unixtime</b>
 *           (не приходит для системных альбомов)
 * @property updated
 *           Дата последнего обновления альбома в формате <b>unixtime</b>
 *           (не приходит для системных альбомов)
 * @property size количество фотографий в альбоме
 */
@JsonIgnoreProperties(ignoreUnknown = true)
data class Album(
        var id: Int = 0,
        @JsonProperty("owner_id")
        var ownerId: Int = 0,
        var title: String = "",
        @JsonDeserialize(using = UnixTimestampDeserializer::class)
        var created: Timestamp = Timestamp(0),
        @JsonDeserialize(using = UnixTimestampDeserializer::class)
        var updated: Timestamp = Timestamp(0),
        var size: Int = 0
)