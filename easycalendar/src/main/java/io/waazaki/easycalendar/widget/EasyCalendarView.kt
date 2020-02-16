package io.waazaki.easycalendar.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.viewpager2.widget.ViewPager2
import io.waazaki.easycalendar.R
import io.waazaki.easycalendar.events.IDateClickedListener
import io.waazaki.easycalendar.events.IMarkers
import io.waazaki.easycalendar.events.IMonthChanged
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
    var onDateChanged: IDateClickedListener? = null
    var onMonthChanged: IMonthChanged? = null
    private var markers = arrayListOf<DateMarker>()
    private var viewPagerCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            // Notify that the month was changed
            val monthObject = calendarBuilder.listMonthObjects[position]
            onMonthChanged?.onMonthChanged(monthObject.month, monthObject.year)
        }
    }

    init {
        initListeners()
    }

    private fun initListeners() {
        // Manage the click on the next button
        buttonNext.setOnClickListener {
            viewPager.currentItem =
                min(viewPager.currentItem + 1, calendarBuilder.listMonthObjects.size - 1)
        }

        // Manage the click on the previous button
        buttonPrevious.setOnClickListener {
            viewPager.currentItem = max(viewPager.currentItem - 1, 0)
        }
    }

    fun setupPager() {
        // Build the date objects
        calendarBuilder.buildCalendar()

        // Setup the months adapter
        adapter = MonthsAdapter(
            context,
            listMonthObjects = calendarBuilder.listMonthObjects,
            onDateClickListener = onDateChanged,
            markers = this
        )
        viewPager.adapter = adapter

        // Change the position to today's month
        val currentPosition = calendarBuilder.getCurrentDatePosition()
        viewPager.setCurrentItem(if (currentPosition == -1) 0 else currentPosition, false)

        // Manage the month selection
        viewPager.registerOnPageChangeCallback(viewPagerCallback)
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

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        viewPager.unregisterOnPageChangeCallback(viewPagerCallback)
    }
}
