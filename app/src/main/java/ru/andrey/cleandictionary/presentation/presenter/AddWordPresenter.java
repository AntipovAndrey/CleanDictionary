package ru.andrey.cleandictionary.presentation.presenter;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ru.andrey.cleandictionary.App;
import ru.andrey.cleandictionary.domain.translation.TranslationInteractor;
import ru.andrey.cleandictionary.model.Translation;
import ru.andrey.cleandictionary.presentation.view.AddWordView;


public class AddWordPresenter {

    @Inject
    TranslationInteractor mTranslationInteractor;

    private AddWordView mView;
    private String mWord;
    private String mLangFrom;
    private String mLangTo;
    private String mTranslation;

    public AddWordPresenter() {
        App.instance.getTranslationComponent().inject(this);
    }

    public Single<Translation> translateWord(Translation translation) {
        return mTranslationInteractor.getTranslation(translation)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public AddWordView getView() {
        return mView;
    }

    public void setView(AddWordView view) {
        mView = view;
        mView.disableButton(true);
    }

    public void updateTranslation(String string) {
        mView.showProgressBar(true);
        mView.disableButton(true);
        mWord = string;
        translateWord(new Translation(string, mLangFrom, mLangTo))
                .map(Translation::getTranslation)
                .subscribe(this::translationSucceed, this::translationError);
    }

    private void translationSucceed(String s) {
        mView.updateTranslation(s);
        mTranslation = s;
        mView.showProgressBar(false);
        mView.disableButton(false);
    }

    private void translationError(Throwable error) {
        mView.errorToast("Error");
        mView.showProgressBar(true);
        mView.disableButton(true);
    }

    public void addWord() {
        final Translation t = new Translation(mWord, mLangFrom, mLangTo);
        t.setTranslation(mTranslation);
        mTranslationInteractor.saveWord(t)
                .subscribe();
        mView.close();
    }

    public void setLangFrom(String langCode) {
        mLangFrom = langCode;
    }

    public void setLangTo(String langCode) {
        mLangTo = langCode;
    }
}
