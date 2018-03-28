package ru.andrey.cleandictionary.domain.translation;

import ru.andrey.cleandictionary.data.repository.InMemoryRepository;
import ru.andrey.cleandictionary.data.repository.TranslationRepository;
import ru.andrey.cleandictionary.model.Translation;
import ru.andrey.cleandictionary.presentation.presenter.DictionaryItem;

public class FavoriteTranslationInteractor {
	private TranslationRepository mRepository = InMemoryRepository.getInstance();

	public void toggleFavorite(DictionaryItem item) {
		final Translation model = item.getTranslationModel();
		model.toggleFavorite();
		mRepository.save(model);
	}
}
