package ru.skillbranch.devintensive.utils.plural

class RussianPlural(
    private val one: String,
    private val few: String,
    private val many: String
) : Plural {
    override fun get(count: Long): String = when {
        count % 10 == 1L && count % 100 != 11L -> one
        count % 10 in 2..4 && (count % 100 < 10 || count % 100 >= 20) -> few
        else -> many
    }
}