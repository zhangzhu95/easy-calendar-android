package io.waazaki.easycalendar.models

import androidx.annotation.ColorRes

data class DateObject(
    var day: Int?,
    var month: Int,
    var year: Int
) {
    override fun toString(): String {
        return "$day/$month/$year"
    }
}

data class MonthObject(
    var listOfDates: List<DateObject>,
    var month: Int,
    var year: Int
)

data class DateMarker(
    var dateObject: DateObject,
    @ColorRes var textColor: Int = android.R.color.white,
    @ColorRes var markColor: Int = android.R.color.holo_red_dark
    )