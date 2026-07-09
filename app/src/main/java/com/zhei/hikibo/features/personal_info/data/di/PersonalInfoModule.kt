package com.zhei.hikibo.features.personal_info.data.di
import com.zhei.hikibo.features.personal_info.data.repository.PersonalInfoImpl
import com.zhei.hikibo.features.personal_info.domain.repository.PersonalInfoRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class PersonalInfoModule {


    @Binds
    @Singleton
    abstract fun bindPersonalInfoRepository(impl: PersonalInfoImpl): PersonalInfoRepository
}