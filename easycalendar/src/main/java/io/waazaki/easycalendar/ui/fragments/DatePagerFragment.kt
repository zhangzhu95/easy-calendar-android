package io.waazaki.easycalendar.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import io.waazaki.easycalendar.R
import io.waazaki.easycalendar.events.IDateClickedListener
import io.waazaki.easycalendar.events.IMarkers
import io.waazaki.easycalendar.models.DateMarker
import io.waazaki.easycalendar.ui.adapters.MonthsAdapter
import io.waazaki.easycalendar.utils.CalendarBuilder
import kotlinx.android.synthetic.main.fragment_date_pager.*
import kotlin.math.max
import kotlin.math.min

class DatePagerFragment : Fragment(), IMarkers {

    private val calendarBuilder = CalendarBuilder()
    private lateinit var adapter: MonthsAdapter
    lateinit var onDateChanged: IDateClickedListener
    private var markers = arrayListOf<DateMarker>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_date_pager, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
            context!!,
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

    fun unmarkDate(dateMarker: DateMarker) {
        markers.remove(dateMarker)
        adapter.notifyDataSetChanged()
    }

    override fun getMarkers(): ArrayList<DateMarker> {
        return markers
    }
}