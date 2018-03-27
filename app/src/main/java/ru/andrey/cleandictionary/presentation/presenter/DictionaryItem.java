package ru.andrey.cleandictionary.presentation.presenter;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import ru.andrey.cleandictionary.domain.translation.FavoriteTranslationInteractor;
import ru.andrey.cleandictionary.domain.translation.TranslationInteractor;
import ru.andrey.cleandictionary.model.Language;
import ru.andrey.cleandictionary.model.Translation;
import ru.andrey.cleandictionary.presentation.view.WordView;

public class DictionaryItem {

	private final Translation mTranslation;
	private WordView mWordView;
	FavoriteTranslationInteractor mInteractor = new FavoriteTranslationInteractor();
	TranslationInteractor mTranslationInteractor = new TranslationInteractor();

	public DictionaryItem(Translation translation) {
		mTranslation = translation;
	}

	public DictionaryItem(String word, String from, String to) {
		mTranslation = new Translation(word,
				Language.valueOf(from),
				Language.valueOf(to),
				false);
	}

	public void translateWord(Runnable onSuccess) {
		mTranslationInteractor.getTranslation(new SingleObserver<Translation>() {
			@Override
			public void onSubscribe(Disposable d) {

			}

			@Override
			public void onSuccess(Translation translation) {
				onSuccess.run();
			}

			@Override
			public void onError(Throwable e) {

			}
		}, AndroidSchedulers.mainThread(), mTranslation);
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
