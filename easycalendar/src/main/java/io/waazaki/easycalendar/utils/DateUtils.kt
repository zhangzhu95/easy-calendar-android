package io.waazaki.easycalendar.utils

import io.waazaki.easycalendar.models.DateObject
import java.util.*

object DateUtils {
    private fun getDate(dateObject: DateObject): Calendar {
        return Calendar.getInstance().apply {
            set(Calendar.DAY_OF_MONTH, dateObject.day.orZero())
            set(Calendar.MONTH, dateObject.month - 1)
            set(Calendar.YEAR, dateObject.year)
        }
    }

    fun isPriorDate(dateObject: DateObject): Boolean {
        val currentDate = Calendar.getInstance()
        val compareDate = getDate(dateObject)

        currentDate.removeTime()
        compareDate.removeTime()

        return currentDate.time.time >= compareDate.time.time
    }

    fun isToday(dateObject: DateObject): Boolean {
        val currentDate = Calendar.getInstance()
        val compareDate = getDate(dateObject)

        currentDate.removeTime()
        compareDate.removeTime()

        return currentDate.time.time == compareDate.time.time
    }
}