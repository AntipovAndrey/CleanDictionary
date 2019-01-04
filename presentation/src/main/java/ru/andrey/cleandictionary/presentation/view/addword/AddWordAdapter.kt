package ru.andrey.cleandictionary.presentation.view.addword

import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import ru.andrey.cleandictionary.R

class AddWordAdapter(private val itemClicked: (String) -> Unit) : ListAdapter<String, Holder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.add_word_item, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val item = getItem(position)
        holder.word.text = item
        holder.root.setOnClickListener { itemClicked(item) }
    }
}

class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var root: View = itemView.findViewById(R.id.root)
    var word: TextView = itemView.findViewById(R.id.word_text)
}

internal class DiffCallback : DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem === newItem
    }
}
