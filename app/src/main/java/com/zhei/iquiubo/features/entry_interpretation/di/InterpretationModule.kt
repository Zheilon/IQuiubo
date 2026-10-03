package com.zhei.iquiubo.features.entry_interpretation.di
import com.zhei.iquiubo.features.entry_interpretation.data.IQuiuboNeuralNetworkRepositoryImpl
import com.zhei.iquiubo.features.entry_interpretation.domain.repository.IQuiuboNeuralNetworkRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class InterpretationModule {

    @Binds
    @Singleton
    abstract fun bindIQuiuboNeuralNetworkRepository(impl: IQuiuboNeuralNetworkRepositoryImpl): IQuiuboNeuralNetworkRepository

}