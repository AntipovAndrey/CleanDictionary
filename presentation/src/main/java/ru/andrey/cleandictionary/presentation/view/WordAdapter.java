package ru.andrey.cleandictionary.presentation.view;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ru.andrey.cleandictionary.R;
import ru.andrey.domain.model.Translation;

public class WordAdapter extends RecyclerView.Adapter<WordAdapter.Holder> {

	private final List<Translation> mDictionaryItems = new ArrayList<>();
	private ItemClicked mItemClicked;

	public WordAdapter(ItemClicked itemClicked) {
		mItemClicked = itemClicked;
		reset();
	}

	@Override
	public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
		final View view = LayoutInflater.from(parent.getContext())
				.inflate(R.layout.dictionary_list_item, parent, false);
		return new Holder(view);
	}

	@Override
	public void onBindViewHolder(Holder holder, int position) {
		final Translation item = mDictionaryItems.get(position);
		holder.header.setText(item.getWord());
		holder.traslation.setText(item.getTranslation());
		holder.langFrom.setText(item.getLanguageFrom().getLanguageCode());
		holder.langTo.setText(item.getLanguageTo().getLanguageCode());
		holder.star.setImageResource(getStarImage(item.getFavorite()));

		holder.star.setOnClickListener(v -> mItemClicked.onStarClicked(item, position));
	}

	private int getStarImage(boolean enabled) {
		return enabled ? R.drawable.ic_star_24dp : R.drawable.ic_no_star_24dp;
	}

	@Override
	public int getItemCount() {
		return mDictionaryItems.size();
	}

	public void add(Translation itemPresenter) {
		mDictionaryItems.add(0, itemPresenter);
		notifyItemInserted(0);
	}

	public void reset() {
		mDictionaryItems.clear();
		notifyDataSetChanged();
	}

	public void remove(Translation item) {
		final int index = mDictionaryItems.indexOf(item);
		if (index != -1) {
			mDictionaryItems.remove(index);
			notifyItemRangeRemoved(index, 1);
		}
	}

	public void replace(Translation translation, int index) {
		mDictionaryItems.set(index, translation);
		notifyItemChanged(index);
	}

	static class Holder extends RecyclerView.ViewHolder {

		TextView header;
		TextView traslation;
		ImageView star;
		TextView langFrom;
		TextView langTo;
		CardView card;

		public Holder(View itemView) {
			super(itemView);
			header = itemView.findViewById(R.id.word_header);
			traslation = itemView.findViewById(R.id.word_translation);
			star = itemView.findViewById(R.id.star_image);
			langFrom = itemView.findViewById(R.id.lang_from);
			langTo = itemView.findViewById(R.id.lang_to);
			card = itemView.findViewById(R.id.word_card);
		}
	}

	interface ItemClicked {
		void onStarClicked(Translation translation, int position);
	}
}
