package com.zhei.iquiubo.core.presentation
import androidx.lifecycle.ViewModel
import com.zhei.iquiubo.core.navigation.Scenes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject


@HiltViewModel
class CoordinatorFlowSceneVM @Inject constructor(): ViewModel() {


    private val _scene = MutableStateFlow<Scenes?>(null)
    val scene: StateFlow<Scenes?> = _scene.asStateFlow()


    fun setScene(scene: Scenes?) {
        _scene.value = scene
    }
}