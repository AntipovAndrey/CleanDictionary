package ru.andrey.cleandictionary.model;


public enum Language {
    RUSSIAN("ru"),
    ENGLISH("en"),
    FINNISH("fi");

    private final String languageCode;

    Language(String languageCode) {
        this.languageCode = languageCode;
    }

    public String getLanguageCode() {
        return languageCode;
    }
}
