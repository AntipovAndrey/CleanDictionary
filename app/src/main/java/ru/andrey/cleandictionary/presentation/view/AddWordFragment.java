package ru.andrey.cleandictionary.presentation.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jakewharton.rxbinding.widget.RxTextView;

import java.util.concurrent.TimeUnit;

import ru.andrey.cleandictionary.R;
import ru.andrey.cleandictionary.presentation.presenter.AddWordPresenter;
import rx.functions.Action1;

/**
 * Created by antipov_an on 28.03.2018.
 */

public class AddWordFragment extends Fragment
		implements AddWordView {

	@LayoutRes
	private static final int sLayout = R.layout.add_word_fragment;

	private EditText mWordEditText;
	private TextView mTranslation;
	private Button mAddButton;

	AddWordPresenter mPresenter = new AddWordPresenter();

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		final View view = inflater.inflate(sLayout, container, false);
		mWordEditText = view.findViewById(R.id.word_edit_text);
		mTranslation = view.findViewById(R.id.translation_text_view);
		mAddButton = view.findViewById(R.id.add_word);
		mAddButton.setOnClickListener(v -> mPresenter.addWord());
		mPresenter.setView(this);
		return view;
	}

	@Override
	public void onStart() {
		super.onStart();
		final Action1<String> updatedAction = string -> {
			mPresenter.updateTranslation(string, getLangFrom(), getLangTo());
		};

		RxTextView.textChanges(mWordEditText)
				.filter(charSequence -> charSequence.length() > 1)
				.debounce(300, TimeUnit.MILLISECONDS)
				.map(CharSequence::toString)
				.observeOn(rx.android.schedulers.AndroidSchedulers.mainThread())
				.subscribe(updatedAction);
	}

	private String getLangFrom() {
		return "ru";
	}

	@Override
	public void onStop() {
		super.onStop();
	}

	public String getLangTo() {
		return "en";
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
