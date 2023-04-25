package com.harshk.splitmates.domain

import android.app.Application
import android.content.Context
import com.harshk.splitmates.FileManager
import com.harshk.splitmates.GoogleService
import com.harshk.splitmates.domain.datasource.MainDataSource
import com.harshk.splitmates.domain.datasource.MainDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class MainDataModule(private val application: Application) {
    companion object {
        @Singleton
        @Provides
        fun bindMainDataSource(dataSource: MainDataSourceImpl): MainDataSource = dataSource

        @Singleton
        @Provides
        fun bindGoogleService(@ApplicationContext context: Context) = GoogleService(context)

        @Singleton
        @Provides
        fun bindFileManager(@ApplicationContext context: Context) = FileManager(context)
    }
}