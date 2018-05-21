package ru.andrey.cleandictionary.presentation.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ru.andrey.cleandictionary.domain.interactor.translation.TranslationInteractor;
import ru.andrey.cleandictionary.domain.model.Translation;
import ru.andrey.cleandictionary.presentation.view.AddWordView;

@InjectViewState
public class AddWordPresenter extends MvpPresenter<AddWordView> {

	@Inject
	TranslationInteractor mTranslationInteractor;

	private String mWord;
	private String mLangFrom;
	private String mLangTo;
	private String mTranslation;

	@Inject
	public AddWordPresenter() {
	}

	public Single<Translation> translateWord(Translation translation) {
		return mTranslationInteractor.getTranslation(translation)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread());
	}

	public void updateTranslation(String string) {
		getViewState().showProgressBar(true);
		getViewState().disableButton(true);
		mWord = string;
		translateWord(new Translation(string, mLangFrom, mLangTo))
				.map(Translation::getTranslation)
				.subscribe(this::translationSucceed, this::translationError);
	}

	private void translationSucceed(String s) {
		getViewState().updateTranslation(s);
		mTranslation = s;
		getViewState().showProgressBar(false);
		getViewState().disableButton(false);
	}

	private void translationError(Throwable error) {
		getViewState().errorToast("Error");
		getViewState().showProgressBar(true);
		getViewState().disableButton(true);
	}

	public void addWord() {
		final Translation t = new Translation(mWord, mLangFrom, mLangTo);
		t.setTranslation(mTranslation);
		mTranslationInteractor.saveWord(t)
				.subscribe();
		getViewState().close();
	}

	public void setLangFrom(String langCode) {
		mLangFrom = langCode;
	}

	public void setLangTo(String langCode) {
		mLangTo = langCode;
	}
}
