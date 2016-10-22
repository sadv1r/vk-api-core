package ru.sadv1r.vk.api.core.exceptions

import ru.sadv1r.vk.api.core.model.Error

/**
 * Класс [VkException] и его подклассы являются исключениями,
 * создающимися на основе ошибок, возвращаемых Вконтакте.
 * Данный класс является полностью самостоятельным, а его подклассы
 * нужны лишь для увеличения удобства использования.
 *
 * @author [sadv1r](http://sadv1r.ru)
 */
open class VkException: Exception {
    val errorCode: Int
    val errorMsg: String
    val requestParams: List<Error.Param>

    /**
     * Создает [VkException] с уточняющим сообщением.
     *
     * @param error ошибка Вконтакте.
     */
    constructor(error: Error) : super(error.errorMsg) {
        this.errorCode = error.errorCode
        this.errorMsg = error.errorMsg
        this.requestParams = error.requestParams
    }
}
