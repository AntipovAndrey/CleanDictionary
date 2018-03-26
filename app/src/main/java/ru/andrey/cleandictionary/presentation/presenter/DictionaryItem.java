package ru.andrey.cleandictionary.presentation.presenter;

import ru.andrey.cleandictionary.domain.translation.FavoriteTranslationInteractor;
import ru.andrey.cleandictionary.model.Translation;
import ru.andrey.cleandictionary.presentation.view.WordView;

public class DictionaryItem {

	private Translation mTranslation;
	private WordView mWordView;
	FavoriteTranslationInteractor mInteractor = new FavoriteTranslationInteractor();

	public DictionaryItem(Translation translation) {
		mTranslation = translation;
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
}
