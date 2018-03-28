package ru.andrey.cleandictionary.presentation.view;

public interface AddWordView {
	void showProgressBar(boolean enabled);

	void updateTranslation(String word);

	void errorToast(String error);

	void disableButton(boolean disabled);

	void close();
}
