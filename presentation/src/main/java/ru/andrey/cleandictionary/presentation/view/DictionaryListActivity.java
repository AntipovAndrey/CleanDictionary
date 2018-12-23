package ru.andrey.cleandictionary.presentation.view;

import android.support.v4.app.Fragment;


public class DictionaryListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new DictionaryListFragment();
    }
}
