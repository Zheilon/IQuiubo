package com.zhei.hikibo.features.login.data.di
import com.zhei.hikibo.core.data.repository.AuthRepositoryImpl
import com.zhei.hikibo.features.login.data.repository.LoginRepositoryImpl
import com.zhei.hikibo.core.domain.repository.AuthRepository
import com.zhei.hikibo.features.login.domain.repository.LoginRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class LoginModule {

    // Pregunta para diseñar:
    // ¿Qué implementación debe utilizar esta abstracción?.

    @Binds
    @Singleton
    abstract fun bindLoginRepository(impl: LoginRepositoryImpl): LoginRepository

}