package ru.andrey.data.api

class MicrosoftApiResponse {

    lateinit var translations: List<MicrosoftTranslation>
}

class MicrosoftTranslation {

    lateinit var displayTarget: String
}

/*
[
  {
    "translations": [
      {
        "normalizedTarget": "строка",
        "displayTarget": "строка",
        "posTag": "NOUN",
        "confidence": 0.7112,
        "prefixWord": "",
        "backTranslations": [
          {
            "normalizedText": "string",
            "displayText": "string",
            "numExamples": 15,
            "frequencyCount": 4766
          },
          {
            "normalizedText": "line",
            "displayText": "line",
            "numExamples": 15,
            "frequencyCount": 3213
          },
          {
            "normalizedText": "row",
            "displayText": "row",
            "numExamples": 15,
            "frequencyCount": 1702
          },
          {
            "normalizedText": "bar",
            "displayText": "bar",
            "numExamples": 15,
            "frequencyCount": 851
          }
        ]
      },
      {
        "normalizedTarget": "значение типа string",
        "displayTarget": "Значение типа String",
        "posTag": "NOUN",
        "confidence": 0.1954,
        "prefixWord": "",
        "backTranslations": [
          {
            "normalizedText": "string",
            "displayText": "string",
            "numExamples": 15,
            "frequencyCount": 224
          }
        ]
      },
      {
        "normalizedTarget": "шнура",
        "displayTarget": "шнура",
        "posTag": "NOUN",
        "confidence": 0.0934,
        "prefixWord": "",
        "backTranslations": [
          {
            "normalizedText": "cord",
            "displayText": "cord",
            "numExamples": 15,
            "frequencyCount": 339
          },
          {
            "normalizedText": "string",
            "displayText": "string",
            "numExamples": 1,
            "frequencyCount": 145
          }
        ]
      }
    ]
  }
]


* */
