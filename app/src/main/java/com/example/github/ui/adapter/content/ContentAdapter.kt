package com.example.github.ui.adapter.content

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.github.R
import com.example.github.databinding.ItemContentBinding
import com.example.github.domain.models.Content

class ContentAdapter(
    private val listener: OnContentClickListener
) : ListAdapter<Content, ContentAdapter.ViewHolder>(
    ContentAdapter.DiffCallback()
) {

    override fun getItemCount() = currentList.size


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemContentBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            ), listener
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }


    class ViewHolder(private val binding: ItemContentBinding, listener: OnContentClickListener) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.run {
                this.listener = listener
            }
        }

        fun bind(item: Content) {
            with(binding) {
                content = item
                when (item.type) {
                    "dir" -> {
                        Glide.with(itemView)
                            .load(R.drawable.package_icon)
                            .into(image)
                    }
                    else -> {
                        Glide.with(itemView)
                            .load(R.drawable.file)
                            .into(image)
                    }
                }
                contentName.text = item.name

            }
        }
    }

    private class DiffCallback : DiffUtil.ItemCallback<Content>() {

        override fun areItemsTheSame(oldItem: Content, newItem: Content): Boolean {
            return oldItem.htmlUrl == newItem.htmlUrl
        }

        override fun areContentsTheSame(oldItem: Content, newItem: Content) =
            oldItem == newItem
    }

    interface OnContentClickListener {
        fun onContentClick(view: View, item: Content)
    }


}