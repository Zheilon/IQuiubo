package com.zhei.ikiora.features.login.data.di
import com.zhei.ikiora.features.login.data.repository.LoginRepositoryImpl
import com.zhei.ikiora.features.login.domain.repository.LoginRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class LoginModule {

    // Pregunta para diseñar:
    // ¿Qué implementación debe devolver esta abstracción?.

    @Binds
    @Singleton
    abstract fun bindLoginRepository(impl: LoginRepositoryImpl): LoginRepository



}