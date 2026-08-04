package com.zhei.iquiubo.core.domain.repository
import com.zhei.iquiubo.core.domain.model.Measurement

interface MeasurementRepository {

    suspend fun getLastMeasurementByUserId(userId: String): Measurement

}