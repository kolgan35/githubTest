package com.example.github.ui.adapter.item

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.github.R
import com.example.github.databinding.ItemRepoBinding
import com.example.github.databinding.ItemUserBinding
import com.example.github.domain.models.GitHubItem
import com.example.github.domain.models.TypeItem

class ItemAdapter(
    private val listener: OnItemClickListener
) : ListAdapter<GitHubItem, ItemAdapter.ViewHolder>(
    DiffCallback()
) {


    override fun getItemCount() = currentList.size

    override fun getItemViewType(position: Int) = currentList[position].getViewType().type


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = when (viewType) {
        TypeItem.User.type -> ViewHolder.UserViewHolder(
            ItemUserBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )
        TypeItem.Repository.type -> ViewHolder.RepositoryViewHolder(
            ItemRepoBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )
        else -> throw IllegalArgumentException("Invalid view type")

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            TypeItem.User.type -> {
                (holder as ViewHolder.UserViewHolder).run {
                    UserViewHolder(this.binding, listener).bind(currentList[position] as GitHubItem.User)
                }
            }
            TypeItem.Repository.type -> {
                (holder as ViewHolder.RepositoryViewHolder).run {
                    RepositoryViewHolder(this.binding, listener).bind(currentList[position] as GitHubItem.Repository)
                }
            }
        }
    }

    class UserViewHolder(private val itemBinding: ItemUserBinding, listener: OnItemClickListener) :
        ViewHolder.UserViewHolder(itemBinding) {

        init {
            binding.run {
                this.listener =listener
            }
        }
        fun bind(item: GitHubItem.User) {
            with(itemBinding) {
                binding.item = item
                Glide.with(itemView)
                    .load(item.avatar)
                    .placeholder(R.drawable.no_image)
                    .into(avatar)
                login.text = item.login
                score.text = item.score
            }

        }
    }

    class RepositoryViewHolder(private val itemBinding: ItemRepoBinding, listener: OnItemClickListener) :
        ViewHolder.RepositoryViewHolder(itemBinding) {
        init {
            binding.run {
                this.listener = listener
            }
        }

        fun bind(item: GitHubItem.Repository) {
            binding.item = item
            with(itemBinding) {
                name.text = item.name
                forks.text = item.forksCount.toString()
                desc.text = item.description
            }
        }
    }


    private class DiffCallback : DiffUtil.ItemCallback<GitHubItem>() {

        override fun areItemsTheSame(oldItem: GitHubItem, newItem: GitHubItem): Boolean {
            return when {
                oldItem is GitHubItem.User && newItem is GitHubItem.User -> oldItem == newItem
                oldItem is GitHubItem.Repository && newItem is GitHubItem.Repository -> oldItem == newItem
                else -> false
            }
        }

        override fun areContentsTheSame(oldItem: GitHubItem, newItem: GitHubItem) =
            oldItem == newItem
    }


    sealed class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        open class UserViewHolder(val binding: ItemUserBinding) : ViewHolder(binding.root)
        open class RepositoryViewHolder(val binding: ItemRepoBinding) : ViewHolder(binding.root)

    }
    interface OnItemClickListener {
        fun onItemClick(view: View, item: GitHubItem)
    }

}