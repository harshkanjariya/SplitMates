package com.harshk.splitmates.core

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.harshk.splitmates.BR

class DataBindingViewHolder<T> constructor(private val binding: ViewDataBinding): RecyclerView.ViewHolder(binding.root) {
   fun bind(item: T, onHold: (item: T) -> Unit) {
      binding.setVariable(BR.item, item)
      binding.executePendingBindings()

      binding.root.setOnLongClickListener {
         onHold(item)
         return@setOnLongClickListener true
      }
   }
}