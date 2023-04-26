package com.harshk.splitmates.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.harshk.splitmates.domain.model.SettingListItem
import com.harshk.splitmates.domain.usecase.SettingsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val useCase: SettingsUseCase
) : ViewModel() {
    private val _splitFiles = MutableSharedFlow<List<SettingListItem>>()
    val splitFiles = _splitFiles.asSharedFlow()
    var selectedList = HashMap<String, SettingListItem>()
    var isSelectionOn = MutableStateFlow(false)

    fun loadFiles() {
        viewModelScope.launch(Dispatchers.IO) {
            _splitFiles.emit(useCase.listFiles())
        }
    }

    fun toggleItemSelection(item: SettingListItem) {
        if (selectedList.containsKey(item.id)) {
            selectedList.remove(item.id)
            if (selectedList.size == 0) {
                isSelectionOn.value = false
            }
        } else {
            selectedList[item.id] = item
            isSelectionOn.value = true
        }
    }

    fun deleteSelected() {
        viewModelScope.launch(Dispatchers.IO) {
            useCase.deleteFiles(selectedList.values.toList())
            val updatedList = ArrayList<SettingListItem>()
            for (file in splitFiles.last()) {
                if (!selectedList.containsKey(file.id)) {
                    updatedList.add(file)
                }
            }
            _splitFiles.emit(updatedList)
            selectedList.clear()
            isSelectionOn.value = false
        }
    }
}