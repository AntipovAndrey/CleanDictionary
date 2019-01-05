package ru.andrey.data.api

class MicrosoftApiResponse {

    lateinit var translations: List<MicrosoftTranslation>
}

class MicrosoftTranslation {

    lateinit var displayTarget: String
}

class MicrosoftLongApiResponse {

    lateinit var translations: List<MicrosoftLongTranslation>
}

class MicrosoftLongTranslation {

    lateinit var text: String
}
