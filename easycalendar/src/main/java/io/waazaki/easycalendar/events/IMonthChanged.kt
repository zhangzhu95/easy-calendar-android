package io.waazaki.easycalendar.events

interface IMonthChanged {
    fun onMonthChanged(month: Int, year: Int)
}