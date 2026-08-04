package com.zhei.iquiubo.core.data.local
import androidx.room.Database
import androidx.room.RoomDatabase
import com.zhei.iquiubo.core.data.local.dao.StateSceneDao
import com.zhei.iquiubo.core.data.local.entity.StateSceneEntity


@Database(
    entities = [
        StateSceneEntity::class,
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase: RoomDatabase() {

    // Utilizando funciones abstractas se pone cada dao que se creo.
    // ejem: abstract fun glucoseDao(): GlucoseDao

    abstract fun stateSceneDao(): StateSceneDao
}