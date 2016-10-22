package ru.sadv1r.vk.api.core.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import ru.sadv1r.vk.api.core.UnixTimestampDeserializer
import java.sql.Timestamp

/**
 * Модель фотографии Вконтакте
 *
 * @author sadv1r
 * @version 0.1
 * @since 0.1
 * @see <a href="https://vk.com/dev/photo">https://vk.com/dev/photo</a>
 *
 * @property id идентификатор фотографии
 * @property albumId идентификатор альбома, в котором находится фотография
 * @property ownerId идентификатор владельца фотографии
 * @property userId
 *           Идентификатор пользователя, загрузившего фото (если фотография размещена в сообществе).
 *           Для фотографий, размещенных от имени сообщества, user_id=100
 * @property text текст описания фотографии
 * @property date дата добавления в формате *unixtime*
 */
@JsonIgnoreProperties(ignoreUnknown = true)
data class Photo(
        val id: Int,
        @JsonProperty("album_id")
        val albumId: Int,
        @JsonProperty("owner_id")
        val ownerId: Int,
        @JsonProperty("user_id")
        val userId: Int,
        val text: String = "",
        @JsonDeserialize(using = UnixTimestampDeserializer::class)
        val date: Timestamp = Timestamp(0)
)