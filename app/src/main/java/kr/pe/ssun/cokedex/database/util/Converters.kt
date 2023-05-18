package kr.pe.ssun.cokedex.database.util

import androidx.room.TypeConverter

class IdsConverter {
    @TypeConverter
    fun stringToListInt(value: String): List<Int> = if (value.isBlank()) {
        listOf()
    } else {
        value.split(":").map { it.toInt() }
    }

    @TypeConverter
    fun listIntToString(value: List<Int>): String = value.fold("") { acc, id ->
        "$acc${ if (acc.isNotBlank()) ":" else "" }$id"
    }
}

class TypesConverter {
    @TypeConverter
    fun stringToListString(value: String): List<String> = value.split(":").map { it }

    @TypeConverter
    fun listStringToString(value: List<String>): String = value.fold("") { acc, type ->
        "$acc${ if (acc.isNotBlank()) ":" else "" }$type"
    }
}

class StatsConverter {
    @TypeConverter
    fun stringToStats(value: String): List<Pair<String, Int>> = value.split(":").map { stat ->
        val x = stat.split(",")
        Pair(x[0], x[1].toInt())
    }

    @TypeConverter
    fun statsToString(value: List<Pair<String, Int>>) = value.fold("") { acc, stat ->
        "$acc${ if (acc.isNotBlank()) ":" else "" }${stat.first},${stat.second}"
    }
}