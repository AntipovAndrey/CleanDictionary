package ru.andrey.cleandictionary.presentation.view;

/**
 * Created by antipov_an on 28.03.2018.
 */

public interface AddWordView {
	void showProgressBar(boolean enabled);

	void updateTranslation(String word);

	void errorToast(String error);

	void disableButton(boolean disabled);

	void close();
}
