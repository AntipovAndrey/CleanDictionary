package ru.andrey.cleandictionary.presentation.view.list;

import android.support.v4.app.Fragment;

import ru.andrey.cleandictionary.presentation.view.SingleFragmentActivity;


public class DictionaryListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new DictionaryListFragment();
    }
}
