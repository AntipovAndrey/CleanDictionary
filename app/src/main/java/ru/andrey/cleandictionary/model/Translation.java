package ru.andrey.cleandictionary.model;


public class Translation {

	private int id;
	private String mWord;
	private String mTranslation;
	private Language mLanguageFrom;
	private Language mLanguageTo;
	private boolean mFavorite;

	public Translation() {
	}

	public Translation(int id,
					   String word,
					   String translation,
					   Language languageFrom,
					   Language languageTo,
					   boolean favorite) {
		this(word, languageFrom, languageTo, favorite);
		this.id = id;
		mTranslation = translation;
	}

	public Translation(String word, Language languageFrom, Language languageTo, boolean favorite) {
		mWord = word;
		mLanguageFrom = languageFrom;
		mLanguageTo = languageTo;
		mFavorite = favorite;
	}

	public Translation(String string, String langFrom, String langTo) {
		for (Language language : Language.values()) {
			if (langFrom.equals(language.getLanguageCode())) {
				mLanguageFrom = language;
			}
			if (langTo.equals(language.getLanguageCode())) {
				mLanguageTo = language;
			}
		}
		mWord = string;
	}

	public int getId() {
		return id;
	}

	public String getWord() {
		return mWord;
	}

	public String getTranslation() {
		return mTranslation;
	}

	public Language getLanguageFrom() {
		return mLanguageFrom;
	}

	public Language getLanguageTo() {
		return mLanguageTo;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setWord(String word) {
		mWord = word;
	}

	public void setTranslation(String translation) {
		mTranslation = translation;
	}

	public void setLanguageFrom(Language languageFrom) {
		mLanguageFrom = languageFrom;
	}

	public void setLanguageTo(Language languageTo) {
		mLanguageTo = languageTo;
	}

	public boolean isFavorite() {
		return mFavorite;
	}

	public void toggleFavorite() {
		mFavorite = !mFavorite;
	}

	public void setFavorite(boolean favorite) {
		mFavorite = favorite;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Translation)) return false;

		Translation that = (Translation) o;

		if (id != that.id) return false;
		if (mWord != null ? !mWord.equals(that.mWord) : that.mWord != null) return false;
		return mTranslation != null ? mTranslation.equals(that.mTranslation) : that.mTranslation == null;
	}

	@Override
	public int hashCode() {
		int result = id;
		result = 31 * result + (mWord != null ? mWord.hashCode() : 0);
		result = 31 * result + (mTranslation != null ? mTranslation.hashCode() : 0);
		return result;
	}
}
