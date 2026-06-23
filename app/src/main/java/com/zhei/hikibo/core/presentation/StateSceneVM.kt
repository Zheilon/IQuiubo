package com.zhei.hikibo.core.presentation
import androidx.lifecycle.ViewModel
import com.zhei.hikibo.core.data.local.dao.StateSceneDao
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class StateSceneVM @Inject constructor(
    private val stateSceneDao: StateSceneDao
) : ViewModel() {



}