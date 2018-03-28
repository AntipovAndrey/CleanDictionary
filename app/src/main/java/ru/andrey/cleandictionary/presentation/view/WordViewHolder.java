package ru.andrey.cleandictionary.presentation.view;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import ru.andrey.cleandictionary.R;

public class WordViewHolder extends RecyclerView.ViewHolder
		implements WordView {

	TextView header;
	TextView traslation;
	ImageView star;
	TextView langFrom;
	TextView langTo;
	CardView card;

	public WordViewHolder(View itemView) {
		super(itemView);
		header = itemView.findViewById(R.id.word_header);
		traslation = itemView.findViewById(R.id.word_translation);
		star = itemView.findViewById(R.id.star_image);
		langFrom = itemView.findViewById(R.id.lang_from);
		langTo = itemView.findViewById(R.id.lang_to);
		card = itemView.findViewById(R.id.word_card);
	}

	@Override
	public void setStar(boolean favorite) {
		star.setImageResource(favorite ? R.drawable.ic_star_24dp : R.drawable.ic_no_star_24dp);
	}
}
