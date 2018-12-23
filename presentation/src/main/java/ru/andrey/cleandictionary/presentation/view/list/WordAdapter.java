package ru.andrey.cleandictionary.presentation.view.list;

import android.content.Context;
import android.graphics.drawable.Drawable;
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

import java.util.List;

import ru.andrey.cleandictionary.R;
import ru.andrey.cleandictionary.presentation.dto.TranslationDto;

public class WordAdapter extends ListAdapter<TranslationDto, WordAdapter.Holder> {

    private ItemClicked mItemClicked;

    private Drawable[] stars = new Drawable[2];

    WordAdapter(ItemClicked itemClicked, Context context) {
        super(DIFF_CALLBACK);
        mItemClicked = itemClicked;
        stars[0] = context.getDrawable(R.drawable.ic_no_star_24dp);
        stars[1] = context.getDrawable(R.drawable.ic_star_24dp);
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
        holder.translation.setText(item.getTranslation());
        holder.langFrom.setText(item.getWord().getFrom());
        holder.langTo.setText(item.getWord().getTo());
        holder.star.setImageDrawable(getStarImage(item.getFavorite()));

        holder.star.setOnClickListener(v -> mItemClicked.onStarClicked(item));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position, @NonNull List<Object> payloads) {
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position);
        }
        for (Object payload : payloads) {
            switch ((ItemPayload) payload) {
                case FAVORITE:
                    holder.star.setImageDrawable(getStarImage(getItem(position).getFavorite()));
            }
        }
    }

    private Drawable getStarImage(boolean enabled) {
        return enabled ? stars[1] : stars[0];
    }

    static class Holder extends RecyclerView.ViewHolder {

        TextView header;
        TextView translation;
        ImageView star;
        TextView langFrom;
        TextView langTo;
        CardView card;

        Holder(View itemView) {
            super(itemView);
            header = itemView.findViewById(R.id.word_header);
            translation = itemView.findViewById(R.id.word_translation);
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

                @Override
                public Object getChangePayload(TranslationDto oldItem, TranslationDto newItem) {
                    if (oldItem.getFavorite() != newItem.getFavorite()) {
                        return ItemPayload.FAVORITE;
                    }
                    return super.getChangePayload(oldItem, newItem);
                }
            };

    enum ItemPayload {
        FAVORITE
    }

    interface ItemClicked {
        void onStarClicked(TranslationDto translation);
    }
}
