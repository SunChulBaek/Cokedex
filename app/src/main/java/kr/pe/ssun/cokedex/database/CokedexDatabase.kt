package kr.pe.ssun.cokedex.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import kr.pe.ssun.cokedex.database.dao.AbilityDao
import kr.pe.ssun.cokedex.database.dao.MoveDao
import kr.pe.ssun.cokedex.database.dao.PokemonDao
import kr.pe.ssun.cokedex.database.model.Ability
import kr.pe.ssun.cokedex.database.model.Move
import kr.pe.ssun.cokedex.database.model.Pokemon
import kr.pe.ssun.cokedex.database.util.IdsConverter
import kr.pe.ssun.cokedex.database.util.StatsConverter
import kr.pe.ssun.cokedex.database.util.TypesConverter

@Database(
    entities = [
        Pokemon::class,
        Ability::class,
        Move::class
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