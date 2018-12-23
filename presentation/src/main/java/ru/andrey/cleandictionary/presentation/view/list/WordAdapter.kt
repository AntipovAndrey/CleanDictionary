package ru.andrey.cleandictionary.presentation.view.list

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import ru.andrey.cleandictionary.R
import ru.andrey.cleandictionary.presentation.dto.TranslationDto
import ru.andrey.cleandictionary.presentation.view.list.WordAdapter.ItemPayload.FAVORITE

class WordAdapter(private val itemClicked: (Int) -> Unit,
                  context: Context) : ListAdapter<TranslationDto, Holder>(DiffCallback()) {

    private val stars = arrayOf(
            context.getDrawable(R.drawable.ic_no_star_24dp),
            context.getDrawable(R.drawable.ic_star_24dp))

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.dictionary_list_item, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val item = getItem(position)

        holder.id = item.id
        holder.header.text = item.word.word
        holder.translation.text = item.translation
        holder.langFrom.text = item.word.from
        holder.langTo.text = item.word.to
        holder.star.setImageDrawable(getStarImage(item.favorite))

        holder.star.setOnClickListener { itemClicked(item.id) }
    }

    override fun onBindViewHolder(holder: Holder, position: Int, payloads: List<Any>) {
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position)
        }
        for (payload in payloads) {
            when (payload as ItemPayload) {
                FAVORITE -> holder.star.setImageDrawable(getStarImage(getItem(position).favorite))
            }
        }
    }

    override fun getItemId(position: Int): Long {
        return getItem(position).id.toLong()
    }

    private fun getStarImage(enabled: Boolean): Drawable {
        return if (enabled) stars[1] else stars[0]
    }

    internal enum class ItemPayload {
        FAVORITE
    }
}

class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    var id: Int = 0
    var header: TextView = itemView.findViewById(R.id.word_header)
    var translation: TextView = itemView.findViewById(R.id.word_translation)
    var star: ImageView = itemView.findViewById(R.id.star_image)
    var langFrom: TextView = itemView.findViewById(R.id.lang_from)
    var langTo: TextView = itemView.findViewById(R.id.lang_to)
}

internal class DiffCallback : DiffUtil.ItemCallback<TranslationDto>() {

    override fun areItemsTheSame(oldDto: TranslationDto, newDto: TranslationDto): Boolean {
        return oldDto.id == newDto.id
    }

    override fun areContentsTheSame(oldDto: TranslationDto, newDto: TranslationDto): Boolean {
        return oldDto == newDto
    }

    override fun getChangePayload(oldItem: TranslationDto, newItem: TranslationDto): Any {
        return if (oldItem.favorite != newItem.favorite) {
            FAVORITE
        } else super.getChangePayload(oldItem, newItem)
    }
}
