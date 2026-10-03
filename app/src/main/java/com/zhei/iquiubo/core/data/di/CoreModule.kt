package com.zhei.iquiubo.core.data.di
import com.zhei.iquiubo.core.data.repository.GeminiProviderRepositoryImpl
import com.zhei.iquiubo.core.data.repository.AuthRepositoryImpl
import com.zhei.iquiubo.core.data.repository.MeasurementRepositoryImpl
import com.zhei.iquiubo.core.data.repository.UserRepositoryImpl
import com.zhei.iquiubo.core.domain.repository.AIProviderRepository
import com.zhei.iquiubo.core.domain.repository.AuthRepository
import com.zhei.iquiubo.core.domain.repository.MeasurementRepository
import com.zhei.iquiubo.core.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class CoreModule {

    @Binds
    @Singleton
    abstract fun bindAuthRepository(impl: AuthRepositoryImpl): AuthRepository

    @Binds
    @Singleton
    abstract fun bindUserRepository(impl: UserRepositoryImpl): UserRepository

    @Binds
    @Singleton
    abstract fun bindMeasurementRepository(impl: MeasurementRepositoryImpl): MeasurementRepository

    @Binds
    @Singleton
    abstract fun bindAIProviderRepository(impl: GeminiProviderRepositoryImpl): AIProviderRepository

}