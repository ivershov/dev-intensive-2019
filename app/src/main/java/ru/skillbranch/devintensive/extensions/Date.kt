package ru.skillbranch.devintensive.extensions

import ru.skillbranch.devintensive.utils.plural.RussianPlural
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.absoluteValue

const val SECOND_IN_MILLIS = 1000L
const val MINUTE_IN_MILLIS = 60 * SECOND_IN_MILLIS
const val HOUR_IN_MILLIS = 60 * MINUTE_IN_MILLIS
const val DAY_IN_MILLIS = 24 * HOUR_IN_MILLIS
const val WEEK_IN_MILLIS = 7 * DAY_IN_MILLIS
const val YEAR_IN_MILLIS = 365 * DAY_IN_MILLIS

private val MINUTE_PLURAL = RussianPlural("минуту", "минуты", "минут")
private val HOUR_PLURAL = RussianPlural("час", "часа", "часов")
private val DAY_PLURAL = RussianPlural("день", "дня", "дней")

enum class TimeUnits(val millis: Long) {
    MILLISECOND(1L),
    SECOND(SECOND_IN_MILLIS),
    MINUTE(MINUTE_IN_MILLIS),
    HOUR(HOUR_IN_MILLIS),
    DAY(DAY_IN_MILLIS),
    WEEK(WEEK_IN_MILLIS),
    YEAR(YEAR_IN_MILLIS)
}

fun Date.format(pattern: String = "HH:mm:ss dd.MM.yy"): String {
    val format = SimpleDateFormat(pattern, Locale("ru"))
    return format.format(this)
}

fun Date.add(value: Int, units: TimeUnits): Date {
    this.time += value * units.millis
    return this
}

fun Date.humanizeDiff(): String {
    var diff = this.time - System.currentTimeMillis()
    val isFuture: Boolean = diff >= 0

    fun format(s: String): String {
        return if (isFuture) "через $s" else "$s назад"
    }

    diff = diff.absoluteValue
    return when (diff) {
        in 0..SECOND_IN_MILLIS -> "только что"
        in SECOND_IN_MILLIS..45 * SECOND_IN_MILLIS -> format("несколько секунд")
        in 45 * SECOND_IN_MILLIS..75 * SECOND_IN_MILLIS -> format("минуту")
        in 75 * SECOND_IN_MILLIS..45 * MINUTE_IN_MILLIS -> format("${diff / MINUTE_IN_MILLIS} ${MINUTE_PLURAL.get(diff / MINUTE_IN_MILLIS)}")
        in 45 * MINUTE_IN_MILLIS..75 * MINUTE_IN_MILLIS -> format("час")
        in 75 * MINUTE_IN_MILLIS..22 * HOUR_IN_MILLIS -> format("${diff / HOUR_IN_MILLIS} ${HOUR_PLURAL.get(diff / HOUR_IN_MILLIS)}")
        in 22 * HOUR_IN_MILLIS..26 * HOUR_IN_MILLIS -> format("день")
        in 26 * HOUR_IN_MILLIS..360 * DAY_IN_MILLIS -> format("${diff / DAY_IN_MILLIS} ${DAY_PLURAL.get(diff / DAY_IN_MILLIS)}")
        else -> if (isFuture) "более чем через год" else "более года назад"
    }
}