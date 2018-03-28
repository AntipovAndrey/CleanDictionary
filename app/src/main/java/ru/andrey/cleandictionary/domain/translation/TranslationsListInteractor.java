package ru.andrey.cleandictionary.domain.translation;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import ru.andrey.cleandictionary.data.repository.InMemoryRepository;
import ru.andrey.cleandictionary.data.repository.TranslationRepository;
import ru.andrey.cleandictionary.model.Translation;
import ru.andrey.cleandictionary.presentation.presenter.DictionaryItem;


public class TranslationsListInteractor {
	TranslationRepository mRepository = InMemoryRepository.getInstance();

	public Single<List<DictionaryItem>> getTranslationList(Scheduler observeOnScheduler) {
		return Single.fromCallable(() -> mRepository.getAll())
				.map(translations -> {
					System.out.println(translations.size());
					final List<DictionaryItem> items = new ArrayList<>(translations.size());
					for (Translation translation : translations) {
						items.add(new DictionaryItem(translation));
					}
					return items;
				}).subscribeOn(Schedulers.io())
				.observeOn(observeOnScheduler);
	}
}
