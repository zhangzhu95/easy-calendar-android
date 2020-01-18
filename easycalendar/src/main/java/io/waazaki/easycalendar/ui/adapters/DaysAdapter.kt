package io.waazaki.easycalendar.ui.adapters

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import io.waazaki.easycalendar.R
import io.waazaki.easycalendar.events.IMarkers
import io.waazaki.easycalendar.models.DateMarker
import io.waazaki.easycalendar.models.DateObject
import io.waazaki.easycalendar.utils.DateUtils.isPriorDate
import io.waazaki.easycalendar.utils.DateUtils.isToday

class DaysAdapter(
    private var context: Context,
    private var dateObjects: List<DateObject>,
    private var markers: IMarkers?
) : BaseAdapter() {

    override fun getView(position: Int, p1: View?, p2: ViewGroup?): View {

        val dateObject = dateObjects[position]
        val root = LayoutInflater.from(context).inflate(R.layout.adapter_days, null)
        val textDate: TextView = root.findViewById(R.id.textDayOfMonth)
        textDate.text = if (dateObject.day != null) dateObject.day.toString() else ""

        val marker = getMarker(dateObject)
        textDate.setBackgroundResource(R.drawable.marker_bg)
        val drawable = textDate.background as GradientDrawable
        if (marker != null) {
            textDate.setTextColor(context.resources.getColor(marker.textColor))
            drawable.setColor(context.resources.getColor(marker.markColor))
        } else {
            textDate.setTextColor(getDateColor(dateObject))
            drawable.setColor(Color.TRANSPARENT)
        }
        return root
    }

    override fun getItem(position: Int): Any {
        return dateObjects[position]
    }

    override fun getItemId(p0: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return dateObjects.size
    }

    private fun getDateColor(dateObject: DateObject): Int {
        return when {
            isToday(dateObject) -> context.resources.getColor(R.color.electricBlue)
            isPriorDate(dateObject) -> context.resources.getColor(R.color.whiteTransparent)
            else -> context.resources.getColor(R.color.white)
        }
    }

    private fun getMarker(dateObject: DateObject): DateMarker? {
        return markers?.getMarkers()?.firstOrNull { marker ->
            marker.dateObject.day == dateObject.day
                    && marker.dateObject.month == dateObject.month
                    && marker.dateObject.year == dateObject.year
        }
    }
}