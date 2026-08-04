package com.zhei.iquiubo.features.entry_interpretation.presentation
import androidx.lifecycle.ViewModel
import com.zhei.iquiubo.core.navigation.Scenes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject


@HiltViewModel
class EntryInterpretationVM @Inject constructor(): ViewModel() {


    private val _navigate = Channel<Scenes>(Channel.BUFFERED)
    val navigate = _navigate.receiveAsFlow()



    fun onButtonSelect(scene: Scenes) {
        _navigate.trySend(scene)
    }
}