package ru.andrey.cleandictionary.presentation.view;

import android.support.v4.app.Fragment;

public class AddWordActivity extends SingleFragmentActivity {
	@Override
	protected Fragment createFragment() {
		return new AddWordFragment();
	}
}
