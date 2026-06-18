package com.zhei.ikiora.core.data.local
import androidx.room.Database
import androidx.room.RoomDatabase
import com.zhei.ikiora.core.data.local.dao.StateSceneDao
import com.zhei.ikiora.core.data.local.entity.StateSceneEntity


@Database(
    entities = [StateSceneEntity::class],
    version = 1,
    exportSchema = false
)
abstract class DiabeatDatabase: RoomDatabase() {

    // Utilizando funciones abstractas se pone cada dao que se creo.
    // ejem: abstract fun glucoseDao(): GlucoseDao

    abstract fun stateSceneDao(): StateSceneDao

}