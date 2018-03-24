package ru.andrey.cleandictionary;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import ru.andrey.cleandictionary.domain.translation.TranslationInteractor;
import ru.andrey.cleandictionary.model.Language;
import ru.andrey.cleandictionary.model.Translation;

public class MainActivity extends AppCompatActivity {

    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button)
                .setOnClickListener(v -> {

                    Translation t = new Translation("Cause", Language.ENGLISH, Language.RUSSIAN);

                    TranslationInteractor interactor = new TranslationInteractor(t);

                    interactor.getTranslation(new SingleObserver<Translation>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            System.out.println("Subscribe");
                        }

                        @Override
                        public void onSuccess(Translation translation) {
                            Toast.makeText(MainActivity.this, translation.getTranslation(), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onError(Throwable e) {
                            System.err.println("Error:" + e);
                        }
                    }, AndroidSchedulers.mainThread());

                });

    }
}
