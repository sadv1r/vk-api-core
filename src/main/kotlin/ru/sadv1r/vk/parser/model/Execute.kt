package ru.sadv1r.vk.parser.model

/**
 * @author [sadv1r](http://sadv1r.ru)
 */
data class Execute(
        private val requests: MutableList<String> = mutableListOf()
) {
    fun append(method: String, args: Map<String, Any?>): Execute {
        requests.add(args
                .filterValues { it != null }
                .asSequence()
                .joinToString(",", "API.$method({", "})") { param ->
                    "${param.key}:${param.value}"
                })

        return this
    }

    fun append(request: String): Execute {
        requests.add(request)

        return this
    }

    fun compose(): String = requests.joinToString("%2b", "return ", ";")

    fun compose(resultVariable: String): String = requests.joinToString(";", postfix = "return $resultVariable;")
}