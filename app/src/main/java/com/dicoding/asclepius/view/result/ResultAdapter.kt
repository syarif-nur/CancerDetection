package com.dicoding.asclepius.view.result

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.asclepius.R
import com.dicoding.asclepius.data.remote.response.ArticleList
import com.dicoding.asclepius.databinding.ItemArticleListBinding

class ResultAdapter : RecyclerView.Adapter<ResultAdapter.ListViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ResultAdapter.ListViewHolder {
        return ListViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_article_list, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ResultAdapter.ListViewHolder, position: Int) {
        val data = differ.currentList[position]
        holder.bind(data)
    }

    override fun getItemCount(): Int = differ.currentList.size

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemArticleListBinding.bind(itemView)
        fun bind(data: ArticleList) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(data.urlToImage)
                    .into(ivItemPhoto)
                tvItemName.text = data.title
                tvItemDescription.text = data.description
            }
        }

    }

    private val differCallback = object : DiffUtil.ItemCallback<ArticleList>() {
        override fun areItemsTheSame(oldItem: ArticleList, newItem: ArticleList): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: ArticleList, newItem: ArticleList): Boolean {
            return oldItem.title == newItem.title
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

}