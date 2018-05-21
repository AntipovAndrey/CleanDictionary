package ru.andrey.cleandictionary.presentation.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.jakewharton.rxbinding.widget.RxAdapterView;
import com.jakewharton.rxbinding.widget.RxTextView;

import java.util.concurrent.TimeUnit;

import ru.andrey.cleandictionary.App;
import ru.andrey.cleandictionary.R;
import ru.andrey.cleandictionary.di.translation.DaggerTranslationComponent;
import ru.andrey.cleandictionary.presentation.presenter.AddWordPresenter;

public class AddWordFragment extends MvpAppCompatFragment
        implements AddWordView {

    @LayoutRes
    private static final int sLayout = R.layout.add_word_fragment;

    private EditText mWordEditText;
    private TextView mTranslation;
    private FloatingActionButton mAddButton;
    private Spinner mLangFromSpinner;
    private Spinner mLangToSpinner;

    @InjectPresenter
    AddWordPresenter mPresenter;

    @ProvidePresenter
    AddWordPresenter providePresenter() {
        return DaggerTranslationComponent.builder()
				.appComponent(App.instance.getAppComponent())
                .build()
				.getAddWordPresenter();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(sLayout, container, false);
        mWordEditText = view.findViewById(R.id.word_edit_text);
        mTranslation = view.findViewById(R.id.translation_text_view);
        mAddButton = view.findViewById(R.id.add_word);
        mLangFromSpinner = view.findViewById(R.id.lang_from_spinner);
        mLangToSpinner = view.findViewById(R.id.lang_to_spinner);


        String[] items = getResources().getStringArray(R.array.languages_spinner_items);

        RxAdapterView.itemSelections(mLangFromSpinner)
                .subscribeOn(rx.android.schedulers.AndroidSchedulers.mainThread())
                .subscribe(i -> mPresenter.setLangFrom(items[i]));

        RxAdapterView.itemSelections(mLangToSpinner)
                .subscribeOn(rx.android.schedulers.AndroidSchedulers.mainThread())
                .subscribe(i -> mPresenter.setLangTo(items[i]));

        mLangFromSpinner.setSelection(0);
        mLangToSpinner.setSelection(1);
        mPresenter.setLangFrom(items[0]);
        mPresenter.setLangTo(items[1]);

        mAddButton.setOnClickListener(v -> mPresenter.addWord());
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        RxTextView.textChanges(mWordEditText)
                .filter(charSequence -> charSequence.length() > 1)
                .debounce(300, TimeUnit.MILLISECONDS)
                .map(CharSequence::toString)
                .observeOn(rx.android.schedulers.AndroidSchedulers.mainThread())
                .subscribe(mPresenter::updateTranslation);
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void showProgressBar(boolean enabled) {
        //todo
    }

    @Override
    public void updateTranslation(String word) {
        mTranslation.setText(word);
    }

    @Override
    public void errorToast(String error) {
        Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void disableButton(boolean disabled) {
        mAddButton.setEnabled(!disabled);
    }

    @Override
    public void close() {
        getActivity().setResult(Activity.RESULT_OK);
        getActivity().finish();
    }
}
