package com.zhei.hikibo.core.navigation
import kotlinx.serialization.Serializable


@Serializable
sealed class Scenes {

    @Serializable
    data object LoginScene: Scenes()

    @Serializable
    data object PersonalInfoScene: Scenes()

    // Estan dentro del scafold

    @Serializable
    data object HomeScene: Scenes()

    // Este bloque constituye las pantallas asociadas al
    // gráfo de navegación de la sección de Interpretation.

    @Serializable
    data object InterpretationGraph

    @Serializable
    sealed class InterpretationScenes: Scenes() {

        @Serializable
        data object Entry: InterpretationScenes()

        @Serializable
        data object Manual: InterpretationScenes()

        @Serializable
        data object Scanner: InterpretationScenes()
    }

    @Serializable
    data object MonitoringScene: Scenes()

    @Serializable
    data object ProfileScene: Scenes()
}