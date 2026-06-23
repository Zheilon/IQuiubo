package com.zhei.hikibo.core.data.local.entity
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "state_scene")
data class StateSceneEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String = "",
    // El valor por defecto de este parametro lo dejo
    // en [ true ], pues cuando el user tenga por
    // primera vez esta app, le va permitir llegar a
    // estas pantallas que tengan estados.
    val state: Boolean = true
)