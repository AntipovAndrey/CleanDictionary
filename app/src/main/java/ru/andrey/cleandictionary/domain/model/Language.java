package ru.andrey.cleandictionary.domain.model;


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

    public static Language byCode(String code) {
        for (Language language : Language.values()) {
            if (code.equalsIgnoreCase(language.getLanguageCode())) {
                return language;
            }
        }
        throw new IllegalArgumentException("Wrong code: " + code);
    }
}
