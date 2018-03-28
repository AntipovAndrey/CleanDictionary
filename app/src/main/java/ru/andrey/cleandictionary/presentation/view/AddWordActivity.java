package ru.andrey.cleandictionary.presentation.view;

import android.support.v4.app.Fragment;

/**
 * Created by antipov_an on 28.03.2018.
 */

public class AddWordActivity extends SingleFragmentActivity {
	@Override
	protected Fragment createFragment() {
		return new AddWordFragment();
	}
}
