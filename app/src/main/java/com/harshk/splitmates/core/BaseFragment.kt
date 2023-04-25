package com.harshk.splitmates.core

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<VB: ViewBinding>(val bindingFactory: (LayoutInflater) -> VB): Fragment() {
    private var _binding: VB? = null
    private val binding: VB
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (_binding == null) {
            _binding = bindingFactory(layoutInflater)
            binding.setViewBindingOnCreateView()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.setViewBindingOnViewCreated()
    }

    abstract fun VB.setViewBindingOnCreateView()

    abstract fun VB.setViewBindingOnViewCreated()
}