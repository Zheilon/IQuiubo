package com.zhei.ikiora.core.di
import android.content.Context
import androidx.room.Room
import com.zhei.ikiora.core.data.local.DiabeatDatabase
import com.zhei.ikiora.core.data.local.dao.StateSceneDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): DiabeatDatabase =
        Room.databaseBuilder(
            context,
            DiabeatDatabase::class.java,
            "diabeat.db"
        ).build()

    // Aquí abajo se ponen los Daos con @Provides nada más.
    // ejem: fun provideGlucoseDao(db: DiabeatDatabase): GlucoseDao = db.glucoseDao

    @Provides
    fun provideStateSceneDao(db: DiabeatDatabase): StateSceneDao = db.stateSceneDao()

}