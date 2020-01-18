package io.waazaki.easycalendar.utils

import java.util.*

fun Calendar.removeTime() {
    set(Calendar.MILLISECOND, 0)
    set(Calendar.SECOND, 0)
    set(Calendar.MINUTE, 0)
    set(Calendar.HOUR, 0)
}

fun Int?.orZero(): Int = this ?: 0