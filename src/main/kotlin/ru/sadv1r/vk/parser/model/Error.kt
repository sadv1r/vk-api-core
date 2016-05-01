package ru.sadv1r.vk.parser.model

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Модель ошибки Вконтакте
 *
 * Created on 4/3/16.
 *
 * @author sadv1r
 * @version 0.1
 * @since 0.1
 * @see <a href="https://vk.com/dev/errors">https://vk.com/dev/errors</a>
 */
data class Error(
        @JsonProperty("error_code")
        val errorCode: Int,
        @JsonProperty("error_msg")
        val errorMsg: String,
        @JsonProperty("request_params")
        val requestParams: List<Param>
) {
    data class Param(
            val key: String,
            val value: String
    )
}

