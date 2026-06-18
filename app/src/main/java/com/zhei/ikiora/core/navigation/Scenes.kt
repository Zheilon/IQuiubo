package com.zhei.ikiora.core.navigation
import kotlinx.serialization.Serializable


@Serializable sealed class Scenes {

    @Serializable data object LoginScene: Scenes()

}