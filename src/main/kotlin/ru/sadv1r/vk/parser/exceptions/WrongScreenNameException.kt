package ru.sadv1r.vk.parser.exceptions

/**
 * Означает, что короткое имя не существует
 *
 * @property vkErrorCode код ошибки Вконтакте
 * @author sadv1r
 * @version 0.1
 * @since 0.1
 */
class WrongScreenNameException : VkException {
    val vkErrorCode = 113

    /**
     * Создает {@code WrongScreenNameException} с {@code null}
     * в качестве сообщения об ошибке.
     */
    constructor() : super()

    /**
     * Создает {@code WrongScreenNameException} с уточняющим сообщением.
     *
     * @param message
     *        Уточняющее сообщение (сохраняемое для последующего
     *        извлечения методом {@link #getMessage()})
     */
    constructor(message: String) : super(message)
}
