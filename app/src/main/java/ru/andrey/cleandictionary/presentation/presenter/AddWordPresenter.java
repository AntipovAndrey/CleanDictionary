package ru.andrey.cleandictionary.presentation.presenter;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import ru.andrey.cleandictionary.domain.translation.TranslationInteractor;
import ru.andrey.cleandictionary.domain.translation.TranslationsListInteractor;
import ru.andrey.cleandictionary.model.Translation;
import ru.andrey.cleandictionary.presentation.view.AddWordView;


public class AddWordPresenter {
    TranslationInteractor mTranslationInteractor = new TranslationInteractor();
    TranslationsListInteractor mListInteractor = new TranslationsListInteractor();

    private AddWordView mView;
    private String mWord;
    private String mLangFrom;
    private String mLangTo;
    private String mTranslation;

    public void translateWord(SingleObserver<String> translationObserver, Translation translation) {
        mTranslationInteractor.getTranslation(new SingleObserver<Translation>() {
            @Override
            public void onSubscribe(Disposable d) {
                translationObserver.onSubscribe(d);
            }

            @Override
            public void onSuccess(Translation translation) {
                translationObserver.onSuccess(translation.getTranslation());
            }

            @Override
            public void onError(Throwable e) {
                translationObserver.onError(e);
            }
        }, AndroidSchedulers.mainThread(), translation);
    }

    public AddWordView getView() {
        return mView;
    }

    public void setView(AddWordView view) {
        mView = view;
    }

    public void updateTranslation(String string) {
        mView.showProgressBar(true);
        mView.disableButton(true);
        mWord = string;
        translateWord(new SingleObserver<String>() {
                          @Override
                          public void onSubscribe(Disposable d) {
                              // no-op
                          }

                          @Override
                          public void onSuccess(String s) {
                              mView.updateTranslation(s);
                              mTranslation = s;
                              mView.showProgressBar(false);
                              mView.disableButton(false);
                          }

                          @Override
                          public void onError(Throwable e) {
                              mView.errorToast("Error");
                              mView.showProgressBar(true);
                              mView.disableButton(true);
                          }
                      },
                new Translation(string, mLangFrom, mLangTo));
    }

    public void addWord() {
        final Translation t = new Translation(mWord, mLangFrom, mLangTo);
        t.setTranslation(mTranslation);
        mTranslationInteractor.saveWord(t);

        mView.close();
    }

    public void setLangFrom(String langCode) {
        mLangFrom = langCode;
    }

    public void setLangTo(String langCode) {
        mLangTo = langCode;
    }
}
