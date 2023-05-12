package kr.pe.ssun.cokedex.model

open class Loadable(
    open val id: Int,
    open val fromDB: Boolean = false
)

fun List<Loadable>.findById(id: Int?): Loadable? = firstOrNull { item ->
    item.id == id
}