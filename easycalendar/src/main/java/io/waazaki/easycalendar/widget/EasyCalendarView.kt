package io.waazaki.easycalendar.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import io.waazaki.easycalendar.R
import io.waazaki.easycalendar.events.IDateClickedListener
import io.waazaki.easycalendar.events.IMarkers
import io.waazaki.easycalendar.models.DateMarker
import io.waazaki.easycalendar.ui.adapters.MonthsAdapter
import io.waazaki.easycalendar.utils.CalendarBuilder
import kotlinx.android.synthetic.main.widget_easy_calendar.view.*
import kotlin.math.max
import kotlin.math.min

class EasyCalendarView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr), IMarkers {
    private var view: View =
        LayoutInflater.from(context).inflate(R.layout.widget_easy_calendar, this, true)

    private val calendarBuilder = CalendarBuilder()
    private lateinit var adapter: MonthsAdapter
    lateinit var onDateChanged: IDateClickedListener
    private var markers = arrayListOf<DateMarker>()

    init {
        initListeners()
    }

    private fun initListeners() {
        buttonNext.setOnClickListener {
            viewPager.currentItem =
                min(viewPager.currentItem + 1, calendarBuilder.listMonthObjects.size - 1)
        }

        buttonPrevious.setOnClickListener {
            viewPager.currentItem = max(viewPager.currentItem - 1, 0)
        }
    }

    fun setupPager() {
        calendarBuilder.buildCalendar()

        adapter = MonthsAdapter(
            context,
            listMonthObjects = calendarBuilder.listMonthObjects,
            onDateClickListener = onDateChanged,
            markers = this
        )

        viewPager.adapter = adapter

        val currentPosition = calendarBuilder.getCurrentDatePosition()
        viewPager.setCurrentItem(if (currentPosition == -1) 0 else currentPosition, false)
    }

    fun clearMarkers() {
        markers.clear()
        adapter.notifyDataSetChanged()
    }

    fun markDate(dateMarker: DateMarker) {
        markers.add(dateMarker)
        adapter.notifyDataSetChanged()
    }

    fun markAllDates(dateMarker: ArrayList<DateMarker>) {
        markers.addAll(dateMarker)
        adapter.notifyDataSetChanged()
    }

    fun unmarkDate(dateMarker: DateMarker) {
        markers.remove(dateMarker)
        adapter.notifyDataSetChanged()
    }

    override fun getMarkers(): ArrayList<DateMarker> {
        return markers
    }
}
