package io.waazaki.easycalendar

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import io.waazaki.easycalendar.events.IDateClickedListener
import io.waazaki.easycalendar.events.IMonthChanged
import io.waazaki.easycalendar.models.DateMarker
import io.waazaki.easycalendar.models.DateObject
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), IDateClickedListener, IMonthChanged {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        calendarView.onDateChanged = this
        calendarView.onMonthChanged = this
        calendarView.setupPager()
        calendarView.markDate(
            dateMarker = DateMarker(
                dateObject = DateObject(10, 1, 2020)
            )
        )
    }

    override fun onDateClickedListener(dateObject: DateObject) {
        Toast.makeText(this, dateObject.toString(), Toast.LENGTH_SHORT).show()
    }

    override fun onMonthChanged(month: Int, year: Int) {
        Toast.makeText(this, "$month/$year", Toast.LENGTH_SHORT).show()
    }
}
