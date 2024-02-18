package com.serj.recommend.android.services.model

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

import com.serj.recommend.android.services.LogService
import com.serj.recommend.android.services.AccountService
import com.serj.recommend.android.services.StorageService
import com.serj.recommend.android.services.ConfigurationService
import com.serj.recommend.android.services.impl.LogServiceImpl
import com.serj.recommend.android.services.impl.AccountServiceImpl
import com.serj.recommend.android.services.impl.StorageServiceImpl
import com.serj.recommend.android.services.impl.ConfigurationServiceImpl

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