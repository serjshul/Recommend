package com.serj.recommend.android.repository.model

import com.serj.recommend.android.repository.AccountService
import com.serj.recommend.android.repository.ConfigurationService
import com.serj.recommend.android.repository.LogService
import com.serj.recommend.android.repository.StorageService
import com.serj.recommend.android.repository.impl.AccountServiceImpl
import com.serj.recommend.android.repository.impl.ConfigurationServiceImpl
import com.serj.recommend.android.repository.impl.LogServiceImpl
import com.serj.recommend.android.repository.impl.StorageServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ServiceModule {
    @Binds
    abstract fun provideAccountService(impl: AccountServiceImpl): AccountService

    @Binds
    abstract fun provideLogService(impl: LogServiceImpl): LogService

    @Binds
    abstract fun provideStorageService(impl: StorageServiceImpl): StorageService

    @Binds
    abstract fun provideConfigurationService(impl: ConfigurationServiceImpl): ConfigurationService
}