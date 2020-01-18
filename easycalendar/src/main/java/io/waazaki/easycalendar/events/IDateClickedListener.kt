package io.waazaki.easycalendar.events

import io.waazaki.easycalendar.models.DateObject

interface IDateClickedListener {
    fun onDateClickedListener(dateObject: DateObject)
}