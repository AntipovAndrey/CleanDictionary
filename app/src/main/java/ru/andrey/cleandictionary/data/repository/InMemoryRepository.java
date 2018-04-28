package ru.andrey.cleandictionary.data.repository;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import ru.andrey.cleandictionary.model.Language;
import ru.andrey.cleandictionary.model.Translation;


public class InMemoryRepository implements TranslationRepository {

    private List<Translation> mWords = new ArrayList<>();
    private int mIncrementor;

    {
        Language EN = Language.ENGLISH;
        Language RU = Language.RUSSIAN;
        Language FI = Language.FINNISH;
        mWords.add(new Translation(++mIncrementor, "Привет", "Hello", RU, EN, false));
        mWords.add(new Translation(++mIncrementor, "Close", "Закрыть", EN, RU, true));
        mWords.add(new Translation(++mIncrementor, "Density", "Плотность", EN, RU, false));
        mWords.add(new Translation(++mIncrementor, "Hyvää päivää", "Hello", FI, EN, false));
        mWords.add(new Translation(++mIncrementor, "Kissa", "Кошка", FI, RU, false));
        mWords.add(new Translation(++mIncrementor, "Managed", "Договорилимь", EN, RU, true));
        mWords.add(new Translation(++mIncrementor, "Мир", "World", RU, EN, true));
        mWords.add(new Translation(++mIncrementor, "Illegal", "Недопустимый", EN, RU, true));
        mWords.add(new Translation(++mIncrementor, "Home", "Дом", EN, RU, false));
        mWords.add(new Translation(++mIncrementor, "Тетрадь", "Notebook", RU, EN, false));
        mWords.add(new Translation(++mIncrementor, "Монитор", "Screen", RU, EN, false));
        mWords.add(new Translation(++mIncrementor, "Uksi", "Одни", FI, RU, false));
        mIncrementor = mWords.size();
    }

    @Override
    public Observable<Translation> getAll() {
        return Observable.fromIterable(mWords)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Single<Translation> findOneById(Integer id) {
        return Single.fromCallable(() -> {
            for (Translation word : mWords) {
                if (id.equals(word.getId())) {
                    return word;
                }
            }
            return null;
        }).subscribeOn(Schedulers.io());
    }

    @Override
    public Completable save(Translation item) {
        return Completable.fromRunnable(() -> {
            for (int i = 0; i < mWords.size(); i++) {
                if (item.getId() == mWords.get(i).getId()) {
                    mWords.set(i, item);
                    return;
                }
            }
            item.setId(++mIncrementor);
            mWords.add(item);
        }).subscribeOn(Schedulers.io());
    }

    @Override
    public Completable deleteById(Integer id) {
        return Completable.fromRunnable(() -> {
            for (Translation word : mWords) {
                if (id.equals(word.getId())) {
                    mWords.remove(word);
                }
            }
        }).subscribeOn(Schedulers.io());
    }

    @Override
    public Completable delete(Translation item) {
        return Completable.fromRunnable(() -> mWords.remove(item))
                .subscribeOn(Schedulers.io());
    }

}
