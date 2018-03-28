package ru.andrey.cleandictionary.presentation.presenter;

import ru.andrey.cleandictionary.domain.translation.FavoriteTranslationInteractor;
import ru.andrey.cleandictionary.model.Language;
import ru.andrey.cleandictionary.model.Translation;
import ru.andrey.cleandictionary.presentation.view.WordView;

public class DictionaryItem {

	private final Translation mTranslation;
	private WordView mWordView;
	FavoriteTranslationInteractor mInteractor = new FavoriteTranslationInteractor();


	public DictionaryItem(Translation translation) {
		mTranslation = translation;
	}

	public DictionaryItem(String word, String from, String to) {
		mTranslation = new Translation(word,
				Language.valueOf(from),
				Language.valueOf(to),
				false);
	}


	public String getHeader() {
		return mTranslation.getWord();
	}

	public String getTranslation() {
		return mTranslation.getTranslation();
	}

	public void starClicked() {
		mInteractor.toggleFavorite(this);
		mWordView.setStar(mTranslation.isFavorite());
	}

	public boolean isFavorite() {
		return mTranslation.isFavorite();
	}

	public String getLangFrom() {
		return mTranslation.getLanguageFrom().getLanguageCode();
	}

	public String getLangTo() {
		return mTranslation.getLanguageTo().getLanguageCode();
	}

	public void setView(WordView wordView) {
		mWordView = wordView;
	}

	public Translation getTranslationModel() {
		return mTranslation;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		DictionaryItem item = (DictionaryItem) o;

		return mTranslation != null ? mTranslation.equals(item.mTranslation) : item.mTranslation == null;
	}

	@Override
	public int hashCode() {
		return mTranslation != null ? mTranslation.hashCode() : 0;
	}
}
