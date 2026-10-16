package com.zhei.iquiubo.core.domain.shared
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.getAndUpdate

/**
 * Clase encargada de compartir datos de un ViewModel (A) a un
 * ViewModel (B)
 * */
abstract class SharedFlowData<T: Any> {

    private val _data = MutableStateFlow<T?>(null)
    val data: StateFlow<T?> = _data.asStateFlow()

    fun set(value: T) { _data.value = value }

    /**
     * Esta función es espcialmente para datos de un solo uso.
     * Devuelve el valor y lo borra.
     * */
    fun take(): T? = _data.getAndUpdate { null }

    fun clean() { _data.value = null }
}