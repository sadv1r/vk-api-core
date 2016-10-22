package ru.sadv1r.vk.api.core.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import ru.sadv1r.vk.api.core.UnixTimestampDeserializer
import java.sql.Timestamp

/**
 * Модель поста Вконтакте
 *
 * @author sadv1r
 * @version 0.1
 * @since 0.1
 * @see <a href="https://vk.com/dev/post">https://vk.com/dev/post</a>
 *
 * @property id идентификатор записи.
 * @property ownerId идентификатор владельца стены, на которой размещена запись.
 * @property fromId идентификатор автора записи.
 * @property date время публикации записи в формате *unixtime*.
 * @property text текст записи.
 * @property replyOwnerId идентификатор владельца записи, в ответ на которую была оставлена текущая.
 * @property replyPostId идентификатор записи, в ответ на которую была оставлена текущая.
 * @property friendsOnly 1, если запись была создана с опцией «Только для друзей».
 * @property comments информация о комментариях к записи
 * @property likes информация о лайках к записи
 * @property reposts информация о репостах записи («Рассказать друзьям»)
 * @property type тип записи
 * @property source информация о способе размещения записи
 * @property attachments информация о вложениях записи
 * @property geo информация о местоположении
 * @property signerId
 *           Идентификатор автора, если запись была опубликована от имени сообщества
 *           и подписана пользователем
 * @property copyHistory
 *           Массив, содержащий историю репостов для записи. Возвращается только в том случае,
 *           если запись является репостом. Каждый из объектов массива, в свою очередь,
 *           является объектом-записью стандартного формата
 * @property canPin
 *           Информация о том, может ли текущий пользователь закрепить запись
 *           (1 — может, 0 — не может)
 * @property canDelete
 *           Информация о том, может ли текущий пользователь удалить запись
 *           (1 — может, 0 — не может)
 * @property canEdit
 *           Информация о том, может ли текущий пользователь редактировать запись
 *           (1 — может, 0 — не может)
 * @property isPinned информация о том, закреплена ли запись (1 — закреплена, 0 — не закреплена)
 */
@JsonIgnoreProperties(ignoreUnknown = true)
data class Post(
        var id: Int,
        @JsonProperty("owner_id")
        var ownerId: Int,
        @JsonProperty("from_id")
        var fromId: Int,
        @JsonDeserialize(using = UnixTimestampDeserializer::class)
        var date: Timestamp,
        var text: String = "",
        @JsonProperty("reply_owner_id")
        var replyOwnerId: Int,
        @JsonProperty("reply_post_id")
        var replyPostId: Int,
        @JsonProperty("friends_only")
        var friendsOnly: Int,
        var comments: Comments,
        var likes: Likes,
        var reposts: Reposts,
        @JsonProperty("post_type")
        var type: Type,
        @JsonProperty("post_source")
        var source: Source?,
        //var attachments: List<Attachment>, FIXME
        var geo: Geo?,
        @JsonProperty("signer_id")
        var signerId: Int,
        @JsonProperty("copy_history")
        var copyHistory: Any?, //FIXME разобраться с форматом
        @JsonProperty("can_pin")
        var canPin: Boolean,
        @JsonProperty("can_delete")
        var canDelete: Boolean,
        @JsonProperty("can_edit")
        var canEdit: Boolean,
        @JsonProperty("is_pinned")
        var isPinned: Boolean
) {
    /**
     * @property count количество комментариев
     * @property canPost
     *           Информация о том, может ли текущий пользователь комментировать запись
     *           (1 — может, 0 — не может)
     */
    data class Comments(
            var count: Int = 0,
            @JsonProperty("can_post")
            var canPost: Boolean
    )

    /**
     * @property count число пользователей, которым понравилась запись
     * @property userLikes наличие отметки «Мне нравится» от текущего пользователя (1 — есть, 0 — нет)
     * @property canLike
     *           Информация о том, может ли текущий пользователь поставить отметку «Мне нравится»
     *           (1 — может, 0 — не может)
     * @property canPublish
     *           Информация о том, может ли текущий пользователь сделать репост записи
     *           (1 — может, 0 — не может)
     */
    data class Likes(
            var count: Int = 0,
            @JsonProperty("user_likes")
            var userLikes: Boolean,
            @JsonProperty("can_like")
            var canLike: Boolean,
            @JsonProperty("can_publish")
            var canPublish: Boolean
    )

    /**
     * @property count число пользователей, скопировавших запись
     * @property userReposted наличие репоста от текущего пользователя (1 — есть, 0 — нет)
     */
    data class Reposts(
            var count: Int = 0,
            @JsonProperty("user_reposted")
            var userReposted: Boolean
    )

    //FIXME у вк в нижнем регистре
    enum class Type {
        @JsonProperty("post")
        POST,
        @JsonProperty("copy")
        COPY,
        @JsonProperty("postpone")
        POSTPONE,
        @JsonProperty("suggest")
        SUGGEST
    }

    //TODO написать класс
    data class Source(
            var lol: Int = 0
    )

    /**
     * @property type тип места
     * @property coordinates координаты места
     * @property place описание места (если оно добавлено)
     */
    data class Geo(
            var type: String = "",
            var coordinates: String = "",
            var place: Place
    ) {
        /**
         * @property id идентификатор места (если назначено)
         * @property title название места (если назначено)
         * @property latitude географическая широта
         * @property longitude географическая долгота
         * @property created дата создания (если назначено)
         * @property icon url изображения-иконки
         * @property country название страны
         * @property city название города
         * @property type тип чекина
         * @property groupId идентификатор сообщества
         * @property groupPhoto миниатюра аватара сообщества
         * @property checkins количество чекинов
         * @property updated время последнего чекина
         * @property address адрес
         */
        data class Place(
                var id: Int = 0,
                var title: String = "",
                var latitude: String = "",  //TODO проверить тип
                var longitude: String = "",  //TODO проверить тип
                @JsonDeserialize(using = UnixTimestampDeserializer::class)
                var created: Timestamp = Timestamp(0),
                var icon: String = "",
                var country: String = "",
                var city: String = "",

                var type: String = "",
                var groupId: Int = 0,
                @JsonProperty("group_photo")
                var groupPhoto: String = "",  //TODO проверить тип
                var checkins: Int = 0,
                @JsonDeserialize(using = UnixTimestampDeserializer::class)
                var updated: Timestamp = Timestamp(0),
                var address: String = ""
        )
    }
}