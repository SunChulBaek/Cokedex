package kr.pe.ssun.cokedex.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import kr.pe.ssun.cokedex.database.dao.AbilityDao
import kr.pe.ssun.cokedex.database.dao.MoveDao
import kr.pe.ssun.cokedex.database.dao.NameDao
import kr.pe.ssun.cokedex.database.dao.PokemonDao
import kr.pe.ssun.cokedex.database.dao.StatDao
import kr.pe.ssun.cokedex.database.dao.TypeDao
import kr.pe.ssun.cokedex.database.dao.ValueDao
import kr.pe.ssun.cokedex.database.model.AbilityEntity
import kr.pe.ssun.cokedex.database.model.MoveEntity
import kr.pe.ssun.cokedex.database.model.NameEntity
import kr.pe.ssun.cokedex.database.model.PokemonAbilityCrossRef
import kr.pe.ssun.cokedex.database.model.PokemonMoveCrossRef
import kr.pe.ssun.cokedex.database.model.PokemonEntity
import kr.pe.ssun.cokedex.database.model.PokemonStatCrossRef
import kr.pe.ssun.cokedex.database.model.PokemonTypeCrossRef
import kr.pe.ssun.cokedex.database.model.StatEntity
import kr.pe.ssun.cokedex.database.model.TypeEntity
import kr.pe.ssun.cokedex.database.model.ValueEntity
import kr.pe.ssun.cokedex.database.util.IdsConverter
import kr.pe.ssun.cokedex.database.util.StatsConverter
import kr.pe.ssun.cokedex.database.util.TypesConverter

@Database(
    entities = [
        PokemonEntity::class,
        NameEntity::class,
        TypeEntity::class,
        AbilityEntity::class,
        MoveEntity::class,
        StatEntity::class,
        ValueEntity::class,
        PokemonStatCrossRef::class,
        PokemonAbilityCrossRef::class,
        PokemonMoveCrossRef::class,
        PokemonTypeCrossRef::class,
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(
    IdsConverter::class,
    TypesConverter::class,
    StatsConverter::class,
)
abstract class CokedexDatabase : RoomDatabase() {
    abstract fun pokemonDao(): PokemonDao
    abstract fun nameDao(): NameDao
    abstract fun typeDao(): TypeDao
    abstract fun statDao(): StatDao
    abstract fun valueDao(): ValueDao
    abstract fun abilityDao(): AbilityDao
    abstract fun moveDao(): MoveDao
}