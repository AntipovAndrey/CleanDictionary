package ru.andrey.cleandictionary.data.repository;

import java.util.ArrayList;
import java.util.List;

import ru.andrey.cleandictionary.model.Language;
import ru.andrey.cleandictionary.model.Translation;



public class InMemoryRepository implements TranslationRepository {

	private List<Translation> mWords = new ArrayList<>();
	private int mIncrementor;
	{
		Language EN = Language.ENGLISH;
		Language RU = Language.RUSSIAN;
		Language FI = Language.FINNISH;
		mWords.add(new Translation(1, "Привет", "Hello", RU, EN, false));
		mWords.add(new Translation(2, "Close", "Закрыть", EN, RU, true));
		mWords.add(new Translation(3, "Density", "Плотность", EN, RU, false));
		mWords.add(new Translation(4, "Hyvää päivää", "Hello", FI, EN, false));
		mWords.add(new Translation(5, "Kissa", "Кошка", FI, RU, false));
		mWords.add(new Translation(6, "Throughout", "На протяжении", EN, RU, true));
		mIncrementor = 6;
	}

	@Override
	public List<Translation> getAll() {
		return new ArrayList<>(mWords);
	}

	@Override
	public Translation findOneById(Integer id) {
		for (Translation word : mWords) {
			if (id.equals(word.getId())) {
				return word;
			}
		}
		return null;
	}

	@Override
	public boolean save(Translation item) {
		for (int i = 0; i < mWords.size(); i++) {
			if (item.getId() == mWords.get(i).getId()) {
				mWords.add(i, item);
				return true;
			}
		}
		item.setId(mIncrementor++);
		mWords.add(item);
		return true;
	}

	@Override
	public boolean deleteById(Integer id) {
		for (Translation word : mWords) {
			if (id.equals(word.getId())) {
				mWords.remove(word);
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean delete(Translation item) {
		return mWords.remove(item);
	}
}
