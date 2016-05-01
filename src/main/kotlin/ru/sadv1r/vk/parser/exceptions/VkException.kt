package ru.sadv1r.vk.parser.exceptions

/**
 * Класс {@code VkException} и его подклассы являются исключениями,
 * создающимися на основе ошибок, возвращаемых Вконтакте.
 *
 * @author sadv1r
 * @version 0.1
 * @since 0.1
 */
open class VkException: Exception {
    /**
     * Создает {@code VkException} с {@code null}
     * в качестве сообщения об ошибке.
     */
    constructor() : super()

    /**
     * Создает {@code VkException} с уточняющим сообщением.
     *
     * @param message
     *        Уточняющее сообщение (сохраняемое для последующего
     *        извлечения методом {@link #getMessage()})
     */
    constructor(message: String) : super(message)
}
