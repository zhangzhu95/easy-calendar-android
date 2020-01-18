package io.waazaki.easycalendar.events

import io.waazaki.easycalendar.models.DateMarker

interface IMarkers {
    fun getMarkers(): ArrayList<DateMarker>
}