package com.zhei.iquiubo.features.entry_interpretation.domain.repository

interface IQuiuboNeuralNetworkRepository {

    fun predict(map: Map<String, Double>): FloatArray

}