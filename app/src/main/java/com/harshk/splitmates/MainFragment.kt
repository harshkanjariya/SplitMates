package com.harshk.splitmates

import androidx.fragment.app.viewModels
import com.harshk.splitmates.core.BaseFragment
import com.harshk.splitmates.databinding.FragmentMainBinding
import com.harshk.splitmates.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : BaseFragment<FragmentMainBinding>(FragmentMainBinding::inflate) {
    private val viewModel: MainViewModel by viewModels()
    override fun FragmentMainBinding.setViewBindingOnViewCreated() {
    }

    override fun FragmentMainBinding.setViewBindingOnCreateView() {
        val account = viewModel.googleAccount
        if (account == null) {
            viewModel.signIn(requireActivity())
        }
    }

}