package io.waazaki.easycalendar.utils

import io.waazaki.easycalendar.models.DateObject
import io.waazaki.easycalendar.models.MonthObject
import java.util.*

class CalendarBuilder {

    val listMonthObjects = arrayListOf<MonthObject>()

    fun buildCalendar() {
        val startYear = getStartYear()
        val endYear = getEndYear()

        (startYear..endYear).forEach { year ->
            (1..12).forEach { month ->
                val listDateObject = getMonthBody(year, month)
                listMonthObjects.add(
                    MonthObject(
                        listDateObject, month, year
                    )
                )
            }
        }
    }

    fun getCurrentDatePosition(): Int {
        return if (listMonthObjects.isEmpty())
            -1
        else {
            listMonthObjects.indexOfFirst {
                it.month == getCurrentMonth() && it.year == getCurrentYear()
            }
        }
    }

    private fun getCurrentMonth() = Calendar.getInstance().get(Calendar.MONTH) + 1

    private fun getCurrentYear() = Calendar.getInstance().get(Calendar.YEAR)

    private fun getEndYear(): Int {
        return getCurrentYear() + 100
    }

    private fun getStartYear(): Int {
        return getCurrentYear() - 100
    }

    /** Print month body  */

    private fun getMonthBody(year: Int, month: Int): List<DateObject> {

        val listDateObject = arrayListOf<DateObject>()

        // Get start day of the week for the first date in the month
        val startDay = getStartDay(year, month)

        // Get number of days in the month
        val numberOfDaysInMonth = getNumberOfDaysInMonth(year, month)

        // Pad space before the first day of the month
        var i = 0
        while (i < startDay) {
            listDateObject.add(
                DateObject(null, month, year)
            )
            i++
        }

        i = 1
        while (i <= numberOfDaysInMonth) {
            listDateObject.add(
                DateObject(i, month, year)
            )
            i++
        }

        return listDateObject
    }


    /** Get the start day of the first day in a month  */
    private fun getStartDay(year: Int, month: Int): Int {

        // Get total number of days since 1/1/1800
        val startDay1800 = 3

        val totalNumberOfDays = getTotalNumberOfDays(year, month)


        // Return the start day
        return (totalNumberOfDays + startDay1800) % 7

    }


    /** Get the total number of days since January 1, 1800  */
    private fun getTotalNumberOfDays(year: Int, month: Int): Int {
        var total = 0

        // Get the total days from 1800 to year - 1
        for (i in 1800 until year)

            if (isLeapYear(i))

                total += 366
            else

                total += 365


        // Add days from Jan to the month prior to the calendar month
        for (i in 1 until month)

            total = total + getNumberOfDaysInMonth(year, i)



        return total

    }


    /** Get the number of days in a month  */

    private fun getNumberOfDaysInMonth(year: Int, month: Int): Int {

        if ((month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12))
            return 31

        if (month == 4 || month == 6 || month == 9 || month == 11)
            return 30

        return if (month == 2) if (isLeapYear(year)) 29 else 28 else 0


        // If month is incorrect
    }


    /** Determine if it is a leap year  */

    private fun isLeapYear(year: Int): Boolean {

        return year % 400 == 0 || (year % 4 == 0 && year % 100 != 0)

    }


    companion object {
        /** Get the English name for the month  */
        fun getMonthName(month: Int): String? {

            var monthName: String? = null

            when (month) {

                1 -> monthName = "January"

                2 -> monthName = "February"

                3 -> monthName = "March"

                4 -> monthName = "April"

                5 -> monthName = "May"

                6 -> monthName = "June"

                7 -> monthName = "July"

                8 -> monthName = "August"

                9 -> monthName = "September"

                10 -> monthName = "October"

                11 -> monthName = "November"

                12 -> monthName = "December"
            }

            return monthName
        }
    }
}