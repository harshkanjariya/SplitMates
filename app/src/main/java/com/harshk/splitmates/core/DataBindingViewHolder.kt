package com.harshk.splitmates.core

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

class DataBindingViewHolder<T> constructor(binding: ViewDataBinding): RecyclerView.ViewHolder(binding.root) {
   fun bind(item: T) {
   }
}