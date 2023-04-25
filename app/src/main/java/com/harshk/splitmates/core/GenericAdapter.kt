package com.harshk.splitmates.core

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter

class GenericAdapter<T> (
    private val viewLayout: Int,
    private val onHold: (item: T) -> Unit,
    areItemsSame: (oldItem: T, newItem: T) -> Boolean,
    areItemContentsEqual: (oldItem: T, newItem: T) -> Boolean = { old, new -> old == new },
): ListAdapter<T, DataBindingViewHolder<T>>(DiffCallback(areItemsSame, areItemContentsEqual)) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataBindingViewHolder<T> {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ViewDataBinding>(
            layoutInflater, viewType, parent, false
        )
        return DataBindingViewHolder(binding)
    }

    override fun getItemViewType(position: Int): Int {
        return viewLayout
    }

    override fun onBindViewHolder(holder: DataBindingViewHolder<T>, position: Int) {
        holder.bind(getItem(position), onHold)
    }

    class DiffCallback<T>(
        val objectEquals: (old: T, new: T) -> Boolean,
        val contentEquals: (old: T, new: T) -> Boolean
    ): DiffUtil.ItemCallback<T>() {
        override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
            return objectEquals(oldItem, newItem)
        }

        override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
            return contentEquals(oldItem, newItem)
        }
    }
}