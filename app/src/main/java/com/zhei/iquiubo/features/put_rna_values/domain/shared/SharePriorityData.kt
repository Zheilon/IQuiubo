package com.zhei.iquiubo.features.put_rna_values.domain.shared
import com.zhei.iquiubo.core.domain.shared.SharedFlowData
import com.zhei.iquiubo.features.put_rna_values.domain.model.PriorityInputModel
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class SharePriorityData @Inject constructor():
    SharedFlowData<PriorityInputModel>() {}