package ru.sadv1r.vk.api.core.exceptions

import ru.sadv1r.vk.api.core.model.Error

/**
 * Означает, что короткое имя не существует
 *
 * @property vkErrorCode код ошибки Вконтакте
 * @author [sadv1r](http://sadv1r.ru)
 */
class WrongScreenNameException : VkException {
    val vkErrorCode = 113

    /**
     * Создает [WrongScreenNameException] с уточняющим сообщением.
     *
     * @param error ошибка Вконтакте.
     */
    constructor(error: Error) : super(error) {
        if (vkErrorCode != error.errorCode)
            throw IllegalArgumentException("Для создания исключения передана неверная ошибка")
    }
}
