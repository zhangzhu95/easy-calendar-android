package io.waazaki.easycalendar.ui.adapters

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.core.content.ContextCompat
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
            // Change Specific date style
            textDate.setTextColor(ContextCompat.getColor(context, marker.textColor))
            drawable.setColor(ContextCompat.getColor(context, marker.markColor))
        } else if (isToday(dateObject)) {
            // Change Today's date style
            textDate.setTextColor(ContextCompat.getColor(context, R.color.black))
            drawable.setColor(Color.WHITE)
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
            isPriorDate(dateObject) -> ContextCompat.getColor(context, R.color.whiteTransparent)
            else -> ContextCompat.getColor(context, R.color.white)
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