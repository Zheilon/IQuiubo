package com.zhei.hikibo.core.navigation
import kotlinx.serialization.Serializable


@Serializable sealed class Scenes {

    @Serializable data object LoginScene: Scenes()

    @Serializable data object PersonalInfoScene: Scenes()
}