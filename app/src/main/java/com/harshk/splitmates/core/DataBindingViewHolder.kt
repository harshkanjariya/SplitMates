package com.harshk.splitmates.core

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.harshk.splitmates.BR

class DataBindingViewHolder<T> constructor(private val binding: ViewDataBinding): RecyclerView.ViewHolder(binding.root) {
   fun bind(
      item: T,
      onClick: (item: T, position: Int) -> Unit,
      onHold: (item: T, position: Int) -> Unit,
      position: Int
   ) {
      binding.setVariable(BR.item, item)
      binding.executePendingBindings()

      binding.root.setOnClickListener {
         onClick(item, position)
      }
      binding.root.setOnLongClickListener {
         onHold(item, position)
         return@setOnLongClickListener true
      }
   }
}