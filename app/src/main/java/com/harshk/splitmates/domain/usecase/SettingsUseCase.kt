package com.harshk.splitmates.domain.usecase

import com.harshk.splitmates.domain.datasource.MainDataSource
import com.harshk.splitmates.domain.model.Group
import javax.inject.Inject

class SettingsUseCase @Inject constructor(
    private val dataSource: MainDataSource
) {
    fun listFiles(): List<Group> {
        return dataSource.loadFiles().map {
            Group(
                id = it.id,
                name = it.name
            )
        }
    }

    fun deleteFiles(files: List<Group>) {
        dataSource.deleteFiles(files)
    }
}