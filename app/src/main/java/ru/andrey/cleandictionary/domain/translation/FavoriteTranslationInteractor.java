package ru.andrey.cleandictionary.domain.translation;

import javax.inject.Inject;

import ru.andrey.cleandictionary.App;
import ru.andrey.cleandictionary.data.repository.TranslationRepository;
import ru.andrey.cleandictionary.model.Translation;
import ru.andrey.cleandictionary.presentation.presenter.DictionaryItemPresenter;

public class FavoriteTranslationInteractor {
	@Inject
	TranslationRepository mRepository;

	public void toggleFavorite(DictionaryItemPresenter item) {
		App.instance.getAppComponent().inject(this);
		final Translation model = item.getTranslationModel();
		model.toggleFavorite();
		mRepository.save(model);
	}
}
