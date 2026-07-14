package com.zhei.hikibo.core.data.di
import com.zhei.hikibo.core.data.repository.AuthRepositoryImpl
import com.zhei.hikibo.core.data.repository.UserRepositoryImpl
import com.zhei.hikibo.core.domain.repository.AuthRepository
import com.zhei.hikibo.core.domain.repository.UserRepository
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
}