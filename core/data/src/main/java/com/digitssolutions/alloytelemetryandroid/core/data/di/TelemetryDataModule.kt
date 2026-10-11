package com.digitssolutions.alloytelemetryandroid.core.data.di

import com.digitssolutions.alloytelemetryandroid.core.data.datasource.AndroidSystemTelemetryDataSource
import com.digitssolutions.alloytelemetryandroid.core.data.datasource.SystemTelemetryDataSource
import com.digitssolutions.alloytelemetryandroid.core.data.repository.SystemTelemetryRepository
import com.digitssolutions.alloytelemetryandroid.core.domain.model.TelemetryReadiness
import com.digitssolutions.alloytelemetryandroid.core.domain.repository.TelemetryRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Binds SystemTelemetryDataSource to AndroidSystemTelemetryDataSource
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class TelemetryDataModule {


    @Binds
    @Singleton
    abstract fun bindTelemetryRepository(
        impl : SystemTelemetryRepository
    ) : TelemetryRepository<TelemetryReadiness>

    @Binds
    @Singleton
    abstract fun bindSystemTelemetryDataSource(
        // When someone asks for SystemTelemetryDataSource, give them this:
        impl: AndroidSystemTelemetryDataSource
    ) : SystemTelemetryDataSource
}