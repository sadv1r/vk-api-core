package ru.sadv1r.vk.parser.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import ru.sadv1r.vk.parser.UnixTimestampDeserializer
import java.sql.Timestamp

/**
 * Модель профиля пользователя Вконтакте
 *
 * @author sadv1r
 * @version 0.1
 * @since 0.1
 * @see <a href="https://vk.com/dev/fields">https://vk.com/dev/fields</a>
 *
 * @property id идентификатор пользователя
 * @property firstName имя пользователя
 * @property lastName фамилия пользователя
 * @property deactivated
 *           Возвращается, если страница пользователя удалена или заблокирована,
 *           содержит значение **deleted** или **banned**. Обратите внимание,
 *           в этом случае дополнительные поля **fields** не возвращаются
 * @property hidden
 *           Возвращается при вызове без *access_token*,если пользователь установил настройку
 *           «Кому в интернете видна моя страница» — «Только пользователям ВКонтакте».
 *           Обратите внимание, в этом случае дополнительные поля **fields** не возвращаются
 * @property photoId
 *           id главной фотографии профиля пользователя в формате **user_id+photo_id**, например, *6492_192164258*.
 *           В некоторых случаях (если фотография была установлена очень давно) это поле не возвращается.
 * @property verified возвращается **1**, если страница пользователя верифицирована, **0** — если не верифицирована
 * @property sex
 *           Пол пользователя. Возможные значения:
 *           *1* — женский;
 *           *2* — мужской;
 *           *0* — пол не указан
 * @property bdate
 *           Дата рождения. Возвращается в формате *DD.MM.YYYY* или *DD.MM* (если год рождения скрыт).
 *           Если дата рождения скрыта целиком, поле отсутствует в ответе
 * @property city информация о городе, указанном на странице пользователя в разделе «Контакты»
 * @property country информация о стране, указанной на странице пользователя в разделе «Контакты»
 * @property homeTown название родного города пользователя
 * @property hasPhoto возвращается **1**, если текущий пользователь установил фотографию для профиля
 * @property photo50
 *           url **квадратной** фотографии пользователя, имеющей ширину 50 пикселей.
 *           В случае отсутствия у пользователя фотографии возвращается
 *           <a href="http://vk.com/images/camera_c.gif">http://vk.com/images/camera_c.gif</a>
 * @property photo100
 *           url **квадратной** фотографии пользователя, имеющей ширину 100 пикселей.
 *           В случае отсутствия у пользователя фотографии возвращается
 *           <a href="http://vk.com/images/camera_b.gif">http://vk.com/images/camera_b.gif</a>
 * @property photo200orig
 *           url фотографии пользователя, имеющей ширину 200 пикселей.
 *           В случае отсутствия у пользователя фотографии возвращается
 *           <a href="http://vk.com/images/camera_a.gif">http://vk.com/images/camera_a.gif</a>
 * @property photo200
 *           url **квадратной** фотографии пользователя, имеющей ширину 200 пикселей.
 *           Если фотография была загружена давно, изображения с такими размерами может не быть,
 *           в этом случае ответ не будет содержать этого поля
 * @property photo400orig
 *           url фотографии пользователя, имеющей ширину 400 пикселей.
 *           Если у пользователя отсутствует фотография такого размера, ответ не будет содержать этого поля
 * @property photoMax
 *           url **квадратной** фотографии пользователя с максимальной шириной.
 *           Может быть возвращена фотография, имеющая ширину как 200, так и 100 пикселей.
 *           В случае отсутствия у пользователя фотографии возвращается
 *           <a href="http://vk.com/images/camera_b.gif">http://vk.com/images/camera_b.gif</a>
 * @property photoMaxOrig
 *           url фотографии пользователя максимального размера.
 *           Может быть возвращена фотография, имеющая ширину как 400, так и 200 пикселей.
 *           В случае отсутствия у пользователя фотографии возвращается
 *           <a href="http://vk.com/images/camera_a.gif">http://vk.com/images/camera_a.gif</a>
 * @property online
 *           Информация о том, находится ли пользователь сейчас на сайте.
 *           Возвращаемые значения: *1* — находится, *0* — не находится.
 *           Если пользователь использует мобильное приложение либо мобильную версию сайта,
 *           возвращается дополнительное поле **online_mobile**, содержащее *1*.
 *           При этом, если используется именно приложение, дополнительно возвращается поле **online_app**,
 *           содержащее его идентификатор.
 * @property lists
 *           Разделенные запятой идентификаторы списков друзей, в которых состоит пользователь.
 *           Поле доступно только для метода [friends.get].
 *           Получить информацию об id и названиях списков друзей можно с помощью метода [friends.getLists].
 *           Если пользователь не состоит ни в одном списке друзей, данное поле отсутствует в ответе
 * @property domain
 *           Короткий адрес страницы. Возвращается строка, содержащая короткий адрес страницы
 *           (возвращается только сам поддомен, например, *andrew*).
 *           Если он не назначен, возвращается **"id"+uid**, например, *id35828305*
 * @property hasMobile
 *           Информация о том, известен ли номер мобильного телефона пользователя.
 *           Возвращаемые значения: 1 — известен, 0 — не известен.
 *           Рекомендуется использовать перед вызовом метода [secure.sendSMSNotification]
 * @property mobilePhone
 *           Номер мобильного телефона пользователя (только для Standalone-приложений).
 *           Если данные указаны и не скрыты настройками приватности
 * @property homePhone
 *           Дополнительный номер телефона пользователя.
 *           Если данные указаны и не скрыты настройками приватности
 * @property site возвращает указанный в профиле сайт пользователя
 * @property education информация о высшем учебном заведении пользователя
 * @property universities список высших учебных заведений, в которых учился текущий пользователь.
 * @property schools список школ, в которых учился пользователь
 * @property status
 *           Статус пользователя. Возвращается строка, содержащая текст статуса,
 *           расположенного в профиле под именем пользователя.
 *           Если у пользователя включена опция «Транслировать в статус играющую музыку»,
 *           будет возвращено дополнительное поле **status_audio**, содержащее информацию о транслируемой композиции
 * @property statusAudio информация о транслируемой композиции
 * @property lastSeen время последнего посещения
 * @property followersCount количество подписчиков пользователя
 * @property commonCount количество общих друзей с текущим пользователем
 * @property counters
 *           Количество различных объектов у пользователя.
 *           Может быть использовано только в методе [users.get] при запросе информации об одном пользователе,
 *           с передачей **access_token**
 *
 * TODO дописать начиная со второй страницы
 */
@JsonIgnoreProperties(ignoreUnknown = true)
data class Profile(
        val id: Int,
        @JsonProperty("first_name")
        val firstName: String,
        @JsonProperty("last_name")
        val lastName: String,
        val deactivated: Deactivated? = null,
        val hidden: Boolean? = null,
        @JsonProperty("photo_id")
        val photoId: String? = null,
        @JsonProperty("verified")
        val verified: Boolean? = null,
        val sex: Int? = null,
        val bdate: String? = null,
        val city: City? = null,
        val country: Country? = null,
        @JsonProperty("home_town")
        val homeTown: String? = null,
        @JsonProperty("has_photo")
        val hasPhoto: Boolean? = null,
        @JsonProperty("photo_50")
        val photo50: String? = null,
        @JsonProperty("photo_100")
        val photo100: String? = null,
        @JsonProperty("photo_200_orig")
        val photo200orig: String? = null,
        @JsonProperty("photo_200")
        val photo200: String? = null,
        @JsonProperty("photo_400_orig")
        val photo400orig: String? = null,
        @JsonProperty("photo_max")
        val photoMax: String? = null,
        @JsonProperty("photo_max_orig")
        val photoMaxOrig: String? = null,
        val online: Boolean? = null,
        val lists: String? = null,
        val domain: String? = null,
        @JsonProperty("has_mobile")
        val hasMobile: Boolean? = null,
        @JsonProperty("mobile_phone")
        val mobilePhone: String? = null,
        @JsonProperty("home_phone")
        val homePhone: String? = null,
        val site: String? = null,
        val education: Education? = null,
        val universities: List<University>? = null,
        val schools: List<School>? = null,
        val status: String? = null,
        @JsonProperty("status_audio")
        val statusAudio: String? = null,
        @JsonProperty("last_seen")
        val lastSeen: LastSeen? = null,
        @JsonProperty("followers_count")
        val followersCount: Int? = null,
        @JsonProperty("common_count")
        val commonCount: Int? = null,
        val counters: Counters? = null

        //TODO дописать начиная со второй страницы
) {
    /**
     * @property DELETED страница пользователя удалена
     * @property BANNED страница пользователя заблокирована
     */
    enum class Deactivated {
        @JsonProperty("deleted")
        DELETED,
        @JsonProperty("banned")
        BANNED
    }

    /**
     * @property id
     *           Идентификатор города, который можно использовать для получения
     *           его названия с помощью метода [database.getCitiesById]
     * @property title название города
     */
    data class City(
            val id: Int,
            val title: String
    )

    /**
     * @property id
     *           Идентификатор страны, который можно использовать для получения
     *           ее названия с помощью метода [database.getCountriesById]
     * @property title название страны
     */
    data class Country(
            val id: Int,
            val title: String
    )

    /**
     * @property university идентификатор университета
     * @property universityName название университета
     * @property faculty идентификатор факультета
     * @property facultyName название факультета
     * @property graduation год окончания
     */
    data class Education (
            val university: Int,
            @JsonProperty("university_name")
            val universityName: String,
            val faculty: Int,
            @JsonProperty("faculty_name")
            val facultyName: String,
            val graduation: Int
    )

    /**
     * @property id идентификатор университета
     * @property country идентификатор страны, в которой расположен университет
     * @property city идентификатор города, в котором расположен университет
     * @property name наименование университета
     * @property faculty идентификатор факультета
     * @property facultyName наименование факультета
     * @property chair идентификатор кафедры
     * @property chairName наименование кафедры
     * @property graduation год окончания обучения
     */
    data class University (
            val id: Int,
            val country: Int,
            val city: Int,
            val name: String,
            val faculty: Int,
            @JsonProperty("faculty_name")
            val facultyName: String,
            val chair: Int,
            @JsonProperty("chair_name")
            val chairName: String,
            val graduation: Int
    )

    /**
     * @property id идентификатор школы
     * @property country идентификатор страны, в которой расположена школа
     * @property city идентификатор города, в котором расположена школа
     * @property name наименование школы
     * @property yearFrom год начала обучения
     * @property yearTo год окончания обучения
     * @property yearGraduated год выпуска
     * @property classLetter буква класса
     * @property speciality специализация
     * @property type идентификатор типа
     * @property typeStr название типа
     *
     * Возможные значения для пар **type-typeStr**:
     * 0 - "школа", 1 - "гимназия", 2 - "лицей", 3 - "школа-интернат", 4 - "школа вечерняя",
     * 5 - "школа музыкальная", 6 - "школа спортивная", 7 - "школа художественная", 8 - "колледж",
     * 9 - "профессиональный лицей", 10 - "техникум", 11 - "ПТУ", 12 - "училище", 13 - "школа искусств"
     */
    data class School (
            val id: String,
            val country: Int,
            val city: Int,
            val name: String,
            @JsonProperty("year_from")
            val yearFrom: Int,
            @JsonProperty("year_to")
            val yearTo: Int,
            @JsonProperty("year_graduated")
            val yearGraduated: Int,
            @JsonProperty("class")
            val classLetter: String,
            val speciality: String,
            val type: Int,
            @JsonProperty("type_str")
            val typeStr: String
    )

    /**
     * @property time время последнего посещения в формате **unixtime**
     * @property platform
     *           Тип платформы, через которую был осуществлён последний вход.
     *           Подробнее смотрите на странице
     *           <a href="https://new.vk.com/dev/using_longpoll">Подключение к LongPoll серверу</a>
     */
    data class LastSeen(
            @JsonDeserialize(using = UnixTimestampDeserializer::class)
            val time: Timestamp,
            val platform: Int
    )

    /**
     * @property albums количество фотоальбомов
     * @property videos количество видеозаписей
     * @property audios количество аудиозаписей
     * @property photos количество фотографий
     * @property notes количество заметок
     * @property friends количество друзей
     * @property groups количество сообществ
     * @property onlineFriends количество друзей онлайн
     * @property mutualFriends количество общих друзей
     * @property userVideos количество видеозаписей с пользователем
     * @property followers количество подписчиков
     * @property pages количество объектов в блоке «Интересные страницы»
     */
    data class Counters(
            val albums: Int,
            val videos: Int,
            val audios: Int,
            val photos: Int,
            val notes: Int,
            val friends: Int,
            val groups: Int,
            @JsonProperty("online_friends")
            val onlineFriends: Int,
            @JsonProperty("mutual_friends")
            val mutualFriends: Int,
            @JsonProperty("user_videos")
            val userVideos: Int,
            val followers: Int,
            val pages: Int
    )
}