package io.waazaki.easycalendar.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.GridView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.waazaki.easycalendar.R
import io.waazaki.easycalendar.events.IDateClickedListener
import io.waazaki.easycalendar.events.IMarkers
import io.waazaki.easycalendar.models.MonthObject
import io.waazaki.easycalendar.utils.CalendarBuilder
import java.util.*

class MonthsAdapter(
    private var context: Context,
    private val listMonthObjects: List<MonthObject>,
    var onDateClickListener: IDateClickedListener?,
    private var markers: IMarkers?
) : RecyclerView.Adapter<MonthsAdapter.MonthsHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MonthsHolder {
        return MonthsHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.month_adapter,
                parent,
                false
            ),
            context,
            onDateClickListener,
            markers
        )
    }

    override fun onBindViewHolder(holder: MonthsHolder, position: Int) {
        holder.bind(listMonthObjects[position])
    }

    override fun getItemCount(): Int = listMonthObjects.size


    class MonthsHolder constructor(
        itemView: View,
        var context: Context,
        var onDateClickListener: IDateClickedListener?,
        var markers: IMarkers?
    ) :
        RecyclerView.ViewHolder(itemView) {

        private var textMonth: TextView = itemView.findViewById(R.id.textMonth)
        private var gridMonthDays: GridView = itemView.findViewById(R.id.gridMonthDays)

        fun bind(monthObject: MonthObject) {

            val text =
                CalendarBuilder.getMonthName(monthObject.month).orEmpty() + " " + monthObject.year
            textMonth.text = text.toUpperCase(Locale.US)

            // Setup the grid
            gridMonthDays.adapter =
                DaysAdapter(
                    context,
                    monthObject.listOfDates,
                    markers
                )

            gridMonthDays.onItemClickListener =
                AdapterView.OnItemClickListener { _, _, index, _ ->
                    val dateObject = monthObject.listOfDates[index]
                    dateObject.day?.let {
                        onDateClickListener?.onDateClickedListener(dateObject)
                    }
                }
        }
    }
}