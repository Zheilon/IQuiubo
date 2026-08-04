package com.zhei.iquiubo.core.data.local.dao
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.zhei.iquiubo.core.data.local.entity.StateSceneEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface StateSceneDao {

    @Query("SELECT * FROM state_scene")
    fun getAll(): Flow<List<StateSceneEntity>>

    @Query("SELECT state FROM state_scene WHERE name =:name LIMIT 1")
    fun observeState(name: String): Flow<Boolean?>

    @Query("SELECT * FROM state_scene WHERE name =:nameState LIMIT 1")
    suspend fun getStateByName(nameState: String) : StateSceneEntity?

    @Query("UPDATE state_scene SET state =:newState WHERE name =:nameState")
    suspend fun updateStateByName(nameState: String, newState: Boolean)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertState(state: StateSceneEntity)
}