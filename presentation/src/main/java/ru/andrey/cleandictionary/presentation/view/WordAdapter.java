package ru.andrey.cleandictionary.presentation.view;

import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import ru.andrey.cleandictionary.R;
import ru.andrey.cleandictionary.presentation.dto.TranslationDto;

public class WordAdapter extends ListAdapter<TranslationDto, WordAdapter.Holder> {

    private ItemClicked mItemClicked;

    public WordAdapter(ItemClicked itemClicked) {
        super(DIFF_CALLBACK);

        mItemClicked = itemClicked;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.dictionary_list_item, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        final TranslationDto item = getItem(position);
        holder.header.setText(item.getWord().getWord());
        holder.traslation.setText(item.getTranslation());
        holder.langFrom.setText(item.getWord().getFrom());
        holder.langTo.setText(item.getId() + ""/*item.getWord().getTo()*/);
        holder.star.setImageResource(getStarImage(item.getFavorite()));

        holder.star.setOnClickListener(v -> mItemClicked.onStarClicked(item, position));
    }

    private int getStarImage(boolean enabled) {
        return enabled ? R.drawable.ic_star_24dp : R.drawable.ic_no_star_24dp;
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

    private static final DiffUtil.ItemCallback<TranslationDto> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<TranslationDto>() {
                @Override
                public boolean areItemsTheSame(TranslationDto oldDto, TranslationDto newDto) {
                    return oldDto.getId() == newDto.getId();
                }

                @Override
                public boolean areContentsTheSame(TranslationDto oldDto, TranslationDto newDto) {
                    return oldDto.equals(newDto);
                }
            };

    interface ItemClicked {
        void onStarClicked(TranslationDto translation, int position);
    }
}
