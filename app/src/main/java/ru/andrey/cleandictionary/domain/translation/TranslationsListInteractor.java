package ru.andrey.cleandictionary.domain.translation;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import ru.andrey.cleandictionary.App;
import ru.andrey.cleandictionary.data.repository.TranslationRepository;
import ru.andrey.cleandictionary.model.Translation;
import ru.andrey.cleandictionary.presentation.presenter.DictionaryItemPresenter;


public class TranslationsListInteractor {

	@Inject
	TranslationRepository mRepository;

	public TranslationsListInteractor() {
		App.instance.getAppComponent().inject(this);
	}

	public Single<List<DictionaryItemPresenter>> getTranslationList(Scheduler observeOnScheduler) {
		return Single.fromCallable(() -> mRepository.getAll())
				.map(translations -> {
					System.out.println(translations.size());
					final List<DictionaryItemPresenter> items = new ArrayList<>(translations.size());
					for (Translation translation : translations) {
						items.add(new DictionaryItemPresenter(translation));
					}
					return items;
				}).subscribeOn(Schedulers.io())
				.observeOn(observeOnScheduler);
	}
}
