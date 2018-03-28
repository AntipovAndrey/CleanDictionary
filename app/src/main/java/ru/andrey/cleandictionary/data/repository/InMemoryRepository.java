package ru.andrey.cleandictionary.data.repository;

import java.util.ArrayList;
import java.util.List;

import ru.andrey.cleandictionary.model.Language;
import ru.andrey.cleandictionary.model.Translation;


public class InMemoryRepository implements TranslationRepository {

	private List<Translation> mWords = new ArrayList<>();
	private int mIncrementor;
	private static InMemoryRepository sInstance;

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

	private InMemoryRepository() {

	}

	@Override
	public List<Translation> getAll() {
		System.err.println(mWords.size());
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
		item.setId(++mIncrementor);
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

	public static TranslationRepository getInstance() {
		if (sInstance == null) {
			synchronized (InMemoryRepository.class) {
				if (sInstance == null) {
					sInstance = new InMemoryRepository();
				}
			}
		}
		return sInstance;
	}
}
