package com.dicoding.asclepius.view.history

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.asclepius.R
import com.dicoding.asclepius.data.local.entity.CancerEntity
import com.dicoding.asclepius.databinding.ItemCancerListBinding

class HistoryAdapter : RecyclerView.Adapter<HistoryAdapter.ListViewHolder>() {

    var onItemClick: ((CancerEntity) -> Unit)? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HistoryAdapter.ListViewHolder {
        return ListViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_cancer_list, parent, false)
        )
    }

    override fun onBindViewHolder(holder: HistoryAdapter.ListViewHolder, position: Int) {
        val data = differ.currentList[position]
        holder.bind(data)
    }

    override fun getItemCount(): Int = differ.currentList.size

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemCancerListBinding.bind(itemView)
        fun bind(data: CancerEntity) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(data.imageUrl)
                    .into(ivItemPhoto)
                tvItemResult.text = data.result
                tvItemScore.text = data.score
            }
        }

        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(differ.currentList[adapterPosition])
            }
        }

    }

    private val differCallback = object : DiffUtil.ItemCallback<CancerEntity>() {
        override fun areItemsTheSame(oldItem: CancerEntity, newItem: CancerEntity): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: CancerEntity, newItem: CancerEntity): Boolean {
            return oldItem.id == newItem.id
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

}