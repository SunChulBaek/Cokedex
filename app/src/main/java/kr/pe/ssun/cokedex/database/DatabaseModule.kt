package kr.pe.ssun.cokedex.database

import android.content.Context
import androidx.room.Room
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
    fun providesCokedexDatabase(
        @ApplicationContext context: Context,
    ): CokedexDatabase = Room.databaseBuilder(
        context,
        CokedexDatabase::class.java,
        "cokedex-database"
    ).createFromAsset("database/preset_1_50.db").build()
}