package com.harshk.splitmates.domain.usecase

import com.harshk.splitmates.domain.datasource.MainDataSource
import com.harshk.splitmates.domain.model.Member
import javax.inject.Inject

class LoadMembersUseCase @Inject constructor(
    private val dataSource: MainDataSource
) {
    operator fun invoke(fileId: String): List<Member>? {
        return dataSource.loadMembers(fileId)
    }
}