package ru.andrey.domain.model

enum class Language(val languageCode: String) {

    RUSSIAN("ru"),
    ENGLISH("en"),
    FINNISH("fi");

    companion object {

        fun byCode(code: String): Language {
            return values().find { code.equals(it.languageCode, ignoreCase = true) }
                    ?: throw IllegalArgumentException("Wrong code: $code")
        }
    }
}
