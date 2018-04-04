package ru.andrey.cleandictionary.presentation.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;

import ru.andrey.cleandictionary.R;
import ru.andrey.cleandictionary.presentation.presenter.DictionaryItemPresenter;

public class WordAdapter extends RecyclerView.Adapter<WordViewHolder> {

	private List<DictionaryItemPresenter> mDictionaryItems;
	private final OnItemClickListener mListener;

	public WordAdapter(List<DictionaryItemPresenter> dictionaryItems,
					   OnItemClickListener listener) {
		Collections.reverse(dictionaryItems);
		mDictionaryItems = new ArrayList<>();
		mDictionaryItems.addAll(new LinkedHashSet<>(dictionaryItems));
		mListener = listener;
	}

	@Override
	public WordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		final View view = LayoutInflater.from(parent.getContext())
				.inflate(R.layout.dictionary_list_item, parent, false);
		return new WordViewHolder(view);
	}

	@Override
	public void onBindViewHolder(WordViewHolder holder, int position) {
		final DictionaryItemPresenter item = mDictionaryItems.get(position);
		item.setView(holder);
		holder.header.setText(item.getHeader());
		holder.traslation.setText(item.getTranslation());
		holder.langFrom.setText(item.getLangFrom());
		holder.langTo.setText(item.getLangTo());
		holder.star.setImageResource(getStarImage(item.isFavorite()));
		holder.star.setOnClickListener(v -> item.starClicked());
		holder.card.setOnClickListener(v -> mListener.onClicked(item));
	}

	private int getStarImage(boolean enabled) {
		return enabled ? R.drawable.ic_star_24dp : R.drawable.ic_no_star_24dp;
	}

	@Override
	public int getItemCount() {
		return mDictionaryItems.size();
	}

	public List<DictionaryItemPresenter> getItemList() {
		return mDictionaryItems;
	}

	public void setList(List<DictionaryItemPresenter> list) {
		if (list != null) {
			mDictionaryItems = list;
			notifyDataSetChanged();
		}
	}

	public interface OnItemClickListener {

		void onClicked(DictionaryItemPresenter item);
	}
}
