package ru.sadv1r.vk.parser.exceptions

/**
 * Означает, что доступ к контенту для текущего пользователя запрещен.
 *
 * @property vkErrorCode код ошибки Вконтакте
 * @author sadv1r
 * @version 0.1
 * @since 0.1
 */
class AccessDeniedException : VkException {
    val vkErrorCode = 15

    /**
     * Создает {@code AccessDeniedException} с {@code null}
     * в качестве сообщения об ошибке.
     */
    constructor() : super()

    /**
     * Создает {@code AccessDeniedException} с уточняющим сообщением.
     *
     * @param message
     *        Уточняющее сообщение (сохраняемое для последующего
     *        извлечения методом {@link #getMessage()})
     */
    constructor(message: String) : super(message)
}
