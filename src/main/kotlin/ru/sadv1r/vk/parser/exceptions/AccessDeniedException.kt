package ru.sadv1r.vk.parser.exceptions

import ru.sadv1r.vk.parser.model.Error

/**
 * Означает, что доступ к контенту для текущего пользователя запрещен.
 *
 * @property vkErrorCode код ошибки Вконтакте
 * @author [sadv1r](http://sadv1r.ru)
 */
class AccessDeniedException : VkException {
    val vkErrorCode = 15

    /**
     * Создает [AccessDeniedException] с уточняющим сообщением.
     *
     * @param error ошибка Вконтакте.
     */
    constructor(error: Error) : super(error) {
        if (vkErrorCode != error.errorCode)
            throw IllegalArgumentException("Для создания исключения передана неверная ошибка")
    }
}
