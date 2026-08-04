package com.zhei.iquiubo.core.data.repository
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.zhei.iquiubo.core.common.FirestoreCollections
import com.zhei.iquiubo.core.data.model.FirestoreMeasurement
import com.zhei.iquiubo.core.domain.model.Measurement
import com.zhei.iquiubo.core.domain.repository.MeasurementRepository
import com.zhei.iquiubo.core.mappers.toDomain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MeasurementRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
): MeasurementRepository {


    override suspend fun getLastMeasurementByUserId(userId: String): Measurement {
        return withContext(Dispatchers.IO) {
            runCatching {
                val snapshot = firestore
                    .collection(FirestoreCollections.USER)
                    .document(userId)
                    .collection(FirestoreCollections.MEASUREMENT)
                    .orderBy("date", Query.Direction.DESCENDING)
                    .limit(1)
                    .get()
                    .await()

                snapshot.documents
                    .firstOrNull()
                    ?.toObject(FirestoreMeasurement::class.java)
                    ?.toDomain()

            }.onFailure {
                Log.e("MeasurementRepository", "Error getting last measurement", it)

            }.getOrNull() ?: FirestoreMeasurement().toDomain()

        }
    }
}