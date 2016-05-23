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
 * @property occupation информация о текущем роде занятия пользователя
 * @property nickname никнейм (отчество) пользователя
 * @property relatives список родственников текущего пользователя
 * @property relation
 *           Семейное положение пользователя:
 *           1 — не женат/не замужем;
 *           2 — есть друг/есть подруга;
 *           3 — помолвлен/помолвлена;
 *           4 — женат/замужем;
 *           5 — всё сложно;
 *           6 — в активном поиске;
 *           7 — влюблён/влюблена;
 *           0 — не указано
 * @property relationPartner партнер, указанный в семейном положении пользователя
 * @property personal информация о полях из раздела «Жизненная позиция»
 * @property skype **Skype** пользователя
 * @property facebook **Facebook** пользователя
 * @property twitter **Twitter** пользователя
 * @property livejournal **Livejournal** пользователя
 * @property instagram **Instagram** пользователя
 * @property exports
 *           Внешние сервисы,
 *           в которые настроен экспорт из ВК (*twitter*, *facebook*, *livejournal*, *instagram*)
 * @property wallComments доступно ли комментирование стены (*1* — доступно, *0* — недоступно)
 * @property activities деятельность
 * @property interests интересы
 * @property music любимая музыка
 * @property movies любимые фильмы
 * @property tv любимые телешоу
 * @property books любимые книги
 * @property games любимые игры
 * @property about «О себе»
 * @property quotes любимые цитаты
 * @property canPost
 *           Информация о том, разрешено ли оставлять записи на стене у пользователя.
 *           Возвращаемые значения: *1* —разрешено, *0* — не разрешено
 * @property canSeeAllPosts
 *           Информация о том, разрешено ли видеть чужие записи на стене пользователя.
 *           Возвращаемые значения: *1* —разрешено, *0* — не разрешено
 * @property canSeeAudio
 *           Информация о том, разрешено ли видеть чужие аудиозаписи на стене пользователя.
 *           Возвращаемые значения: *1* —разрешено, *0* — не разрешено
 * @property canWritePrivateMessage
 *           Информация о том, разрешено ли написание личных сообщений данному пользователю.
 *           Возвращаемые значения: *1* —разрешено, *0* — не разрешено
 * @property canSendFriendRequest
 *           Информация о том, будет ли отправлено уведомление пользователю о заявке в друзья.
 *           Возвращаемые значения: *1* — уведомление будет отправлено, *0* — уведомление не будет оптравлено
 * @property isFavorite возвращается *1*, если пользователь находится в закладках у текущего пользователя
 * @property isHiddenFromFeed возвращается *1*, если пользователь скрыт в новостях у текущего пользователя
 * @property timezone временная зона пользователя. Возвращается только при запросе информации о текущем пользователе
 * @property screenName короткое имя (поддомен) страницы пользователя
 * @property maidenName девичья фамилия
 * @property cropPhoto возвращает данные о точках, по которым вырезаны профильная и миниатюрная фотографии пользователя
 * @property isFriend *1* – пользователь друг, *2* – пользователь не в друзьях
 * @property friendStatus
 *           Статус дружбы с пользователем:
 *           0 – пользователь не является другом;
 *           1 – отправлена заявка/подписка пользователю;
 *           2 – имеется входящая заявка/подписка от пользователя;
 *           3 – пользователь является другом
 * @property career информация о карьере пользователя
 * @property military информация о военной службе пользователя
 * @property blacklisted
 *           Возвращается 1, если текущий пользователь находится в
 *           черном списке у запрашиваемого пользователя
 * @property blacklistedByMe
 *           Возвращается 1, если запрашиваемый пользователь находится в
 *           черном списке у текущего пользователя
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
        val counters: Counters? = null,
        val occupation: Occupation? = null,
        val nickname: String? = null,
        val relatives: List<Relatives>? = null,
        val relation: Int? = null,
        @JsonProperty("relation_partner")
        val relationPartner: RelationPartner? = null,
        val personal: Personal? = null,
        val skype: String? = null,
        val facebook: String? = null,
        val twitter: String? = null,
        val livejournal: String? = null,
        val instagram: String? = null,
        val exports: Any? = null, //FIXME разобраться с форматом
        @JsonProperty("wall_comments")
        val wallComments: Int? = null,
        val activities: String? = null,
        val interests: String? = null,
        val music: String? = null,
        val movies: String? = null,
        val tv: String? = null,
        val books: String? = null,
        val games: String? = null,
        val about: String? = null,
        val quotes: String? = null,
        @JsonProperty("can_post")
        val canPost: Boolean? = null,
        @JsonProperty("can_see_all_posts")
        val canSeeAllPosts: Boolean? = null,
        @JsonProperty("canSeeAudio")
        val canSeeAudio: Boolean? = null,
        @JsonProperty("can_write_private_message")
        val canWritePrivateMessage: Boolean? = null,
        @JsonProperty("can_send_friend_request")
        val canSendFriendRequest: Boolean? = null,
        @JsonProperty("is_favorite")
        val isFavorite: Boolean? = null,
        @JsonProperty("is_hidden_from_feed")
        val isHiddenFromFeed: Boolean? = null,
        val timezone: Int? = null,
        @JsonProperty("screen_name")
        val screenName: String? = null,
        @JsonProperty("maiden_name")
        val maidenName: String? = null,
        @JsonProperty("crop_photo")
        val cropPhoto: CropPhoto? = null,
        @JsonProperty("is_friend")
        val isFriend: Boolean? = null,
        @JsonProperty("friend_status")
        val friendStatus: Int? = null,
        val career: List<Career>? = null,
        val military: Military? = null,
        val blacklisted: Boolean? = null,
        @JsonProperty("blacklisted_by_me")
        val blacklistedByMe: Boolean? = null
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

    /**
     * @property type тип занятия
     * @property id идентификатор школы, вуза, группы компании (в которой пользователь работает)
     * @property name название школы, вуза или места работы
     */
    data class Occupation(
            val type: Type,
            val id: Int,
            val name: String
    ) {
        enum class Type {
            @JsonProperty("work")
            WORK,
            @JsonProperty("school")
            SCHOOL,
            @JsonProperty("university")
            UNIVERSITY
        }
    }

    /**
     * @property id идентификатор пользователя Вконтакте, являющегося родственником
     * @property name когда родственник не является пользователем ВКонтакте (вместо [id])
     * @property type тип родственных связей
     */
    data class Relatives(
            val id: Int,
            val name: String,
            val type: Type
    ) {
        /**
         * @property SIBLING брат/сестра
         * @property PARENT родитель
         * @property CHILD ребенок
         * @property GRANDPARENT дедушка/бабушка
         * @property GRANDCHILD внук
         */
        enum class Type {
            @JsonProperty("sibling")
            SIBLING,
            @JsonProperty("parent")
            PARENT,
            @JsonProperty("child")
            CHILD,
            @JsonProperty("grandparent")
            GRANDPARENT,
            @JsonProperty("grandchild")
            GRANDCHILD
        }
    }

    /**
     * @property id идентификатор пользователя Вконтакте, являющегося партнером
     * @property name имя партнера
     */
    data class RelationPartner(
            val id: Int,
            val name: String
    )

    /**
     * @property political:
     *           Политические предпочтения
     *           1 — коммунистические;
     *           2 — социалистические;
     *           3 — умеренные;
     *           4 — либеральные;
     *           5 — консервативные;
     *           6 — монархические;
     *           7 — ультраконсервативные;
     *           8 — индифферентные;
     *           9 — либертарианские
     * @property langs языки
     * @property religion мировоззрение
     * @property inspiredBy источники вдохновения
     * @property peopleMain
     *           Главное в людях:
     *           1 — ум и креативность;
     *           2 — доброта и честность;
     *           3 — красота и здоровье;
     *           4 — власть и богатство;
     *           5 — смелость и упорство;
     *           6 — юмор и жизнелюбие
     * @property lifeMain
     *           Главное в жизни:
     *           1 — семья и дети;
     *           2 — карьера и деньги;
     *           3 — развлечения и отдых;
     *           4 — наука и исследования;
     *           5 — совершенствование мира;
     *           6 — саморазвитие;
     *           7 — красота и искусство;
     *           8 — слава и влияние;
     * @property smoking
     *           Отношение к курению:
     *           1 — резко негативное;
     *           2 — негативное;
     *           3 — нейтральное;
     *           4 — компромиссное;
     *           5 — положительное
     * @property alcohol
     *           Отношение к алкоголю:
     *           1 — резко негативное;
     *           2 — негативное;
     *           3 — нейтральное;
     *           4 — компромиссное;
     *           5 — положительное
     */
    data class Personal(
            val political: Int,
            val langs: List<String>,
            val religion: String,
            @JsonProperty("inspired_by")
            val inspiredBy: String,
            @JsonProperty("people_main")
            val peopleMain: Int,
            @JsonProperty("life_main")
            val lifeMain: Int,
            val smoking: Int,
            val alcohol: Int
    )

    /**
     * @property photo объект [Photo] фотографии пользователя из которой вырезается профильная аватарка
     * @property crop вырезанная фотография пользователя, координаты указаны в процентах
     * @property rect миниатюрная квадратная фотография, вырезанная из фотографии [crop], координаты указаны в процентах
     */
    data class CropPhoto(
            val photo: Photo,
            val crop: Coordinates,
            val rect: Coordinates
    ) {
        data class Coordinates(
                val x: Float,
                val y: Float,
                val x2: Float,
                val y2: Float
        )
    }

    /**
     * @property groupId идентификатор сообщества (если доступно, иначе [company])
     * @property company название организации (если доступно, иначе [groupId])
     * @property countryId идентификатор страны
     * @property cityId идентификатор города (если доступно, иначе [cityName])
     * @property cityName название города (если доступно, иначе [cityId])
     * @property from год начала работы
     * @property until год окончания работы
     * @property position должность
     */
    data class Career(
            @JsonProperty("group_id")
            val groupId: Int,
            val company: String,
            @JsonProperty("country_id")
            val countryId: Int,
            @JsonProperty("city_id")
            val cityId: Int,
            @JsonProperty("city_name")
            val cityName: String,
            val from: Int,
            val until: Int,
            val position: String
    )

    /**
     * @property unit номер части
     * @property unitId идентификатор части в базе данных
     * @property countryId идентификатор страны, в которой находится часть
     * @property from год начала службы
     * @property unit год окончания службы
     */
    data class Military(
            val unit: Int,
            @JsonProperty("unit_id")
            val unitId: Int,
            @JsonProperty("country_id")
            val countryId: Int,
            val from: Int,
            val until: Int
    )
}