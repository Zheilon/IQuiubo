package com.zhei.iquiubo.features.put_rna_values.domain.usecase
import com.zhei.iquiubo.core.domain.model.User
import javax.inject.Inject
import kotlin.math.pow
import kotlin.math.roundToInt


class GetBmiUseCase @Inject constructor() {

    operator fun invoke(user: User): Double? {
        val weight = user.weightKG
        val height = user.heightCM / 100.0

        if (weight <= 0 || height <= 0)
            return null

        val bmi = weight / (height * height)
        return (bmi * 10).roundToInt() / 10.0
    }

}