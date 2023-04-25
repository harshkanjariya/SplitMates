package com.harshk.splitmates.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.harshk.splitmates.domain.model.SettingListItem
import com.harshk.splitmates.domain.usecase.SettingsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val useCase: SettingsUseCase
) : ViewModel() {
    private val _splitFiles = MutableSharedFlow<List<SettingListItem>>()
    val splitFiles = _splitFiles.asSharedFlow()

    fun loadFiles() {
        viewModelScope.launch(Dispatchers.IO) {
            _splitFiles.emit(useCase.listFiles())
        }
    }
}