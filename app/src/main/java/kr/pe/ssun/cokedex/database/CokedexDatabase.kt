package kr.pe.ssun.cokedex.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import kr.pe.ssun.cokedex.database.dao.AbilityDao
import kr.pe.ssun.cokedex.database.dao.MoveDao
import kr.pe.ssun.cokedex.database.dao.PokemonDao
import kr.pe.ssun.cokedex.database.model.AbilityEntity
import kr.pe.ssun.cokedex.database.model.MoveEntity
import kr.pe.ssun.cokedex.database.model.PokemonEntity
import kr.pe.ssun.cokedex.database.util.IdsConverter
import kr.pe.ssun.cokedex.database.util.StatsConverter
import kr.pe.ssun.cokedex.database.util.TypesConverter

@Database(
    entities = [
        PokemonEntity::class,
        AbilityEntity::class,
        MoveEntity::class
    ],
    version = 1,
)
@TypeConverters(
    IdsConverter::class,
    TypesConverter::class,
    StatsConverter::class,
)
abstract class CokedexDatabase : RoomDatabase() {
    abstract fun pokemonDao(): PokemonDao
    abstract fun abilityDao(): AbilityDao
    abstract fun moveDao(): MoveDao
}