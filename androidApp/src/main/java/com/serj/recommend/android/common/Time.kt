package com.serj.recommend.android.common

// TODO: Push this file to shared folder,
//  using: https://github.com/Kotlin/kotlinx-datetime
import java.util.Calendar
import java.util.Date

private val months = listOf(
    "January", "February", "March", "April", "May", "June", "July", "August",
    "September", "October", "November", "December"
)

fun getCreatedTime(date: Date): String {
    val itemCalendar = Calendar.getInstance()
    itemCalendar.time = date
    val currentCalendar = Calendar.getInstance()
    currentCalendar.time = Date()
    val checkCalendar = Calendar.getInstance()

    checkCalendar.time = Date(Date().time - 60 * 1000)
    if (itemCalendar.after(checkCalendar)) {
        val currentSecond = currentCalendar.get(Calendar.SECOND)
        val itemSecond = itemCalendar.get((Calendar.SECOND))
        val difference = if ((currentSecond - itemSecond) >= 0) currentSecond - itemSecond
        else 60 + (currentSecond - itemSecond)
        return "${difference}s"
    }
    checkCalendar.time = Date(Date().time - 60 * 60 * 1000)
    if (itemCalendar.after(checkCalendar)) {
        val currentMinute = currentCalendar.get(Calendar.MINUTE)
        val itemMinute = itemCalendar.get((Calendar.MINUTE))
        val difference = if ((currentMinute - itemMinute) >= 0) currentMinute - itemMinute
        else 60 + (currentMinute - itemMinute)
        return "${difference}m"
    }
    checkCalendar.time = Date(Date().time - 24 * 60 * 60 * 1000)
    if (itemCalendar.after(checkCalendar)) {
        val currentHour = currentCalendar.get(Calendar.HOUR_OF_DAY)
        val itemHour = itemCalendar.get((Calendar.HOUR_OF_DAY))
        val difference = if ((currentHour - itemHour) >= 0) currentHour - itemHour
        else 24 + (currentHour - itemHour)
        return "${difference}h"
    }
    checkCalendar.time = Date(Date().time - 7 * 24 * 60 * 60 * 1000L)
    if (itemCalendar.after(checkCalendar)) {
        val currentDay = currentCalendar.get(Calendar.DAY_OF_YEAR)
        val itemDay = itemCalendar.get((Calendar.DAY_OF_YEAR))
        val difference = if ((currentDay - itemDay) >= 0) currentDay - itemDay
        else 7 + (currentDay - itemDay)
        return "${difference}d"
    }
    checkCalendar.time = Date(Date().time - 365 * 24 * 60 * 60 * 1000L)
    return if (itemCalendar.after(checkCalendar)) {
        val currentDay = currentCalendar.get(Calendar.DAY_OF_YEAR)
        val itemDay = itemCalendar.get((Calendar.DAY_OF_YEAR))
        val difference = if ((currentDay - itemDay) >= 0) currentDay - itemDay
        else 365 + (currentDay - itemDay)
        "${difference / 7}w"
    } else {
        val currentYear = currentCalendar.get(Calendar.YEAR)
        val itemYear = itemCalendar.get((Calendar.YEAR))
        val difference = currentYear - itemYear
        "${difference}y"
    }
}

fun getMonthAndDay(date: Date): String {
    val calendar = Calendar.getInstance()
    calendar.time = date

    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    return "${months[month]} $day"
}

fun getYear(date: Date): String {
    val calendar = Calendar.getInstance()
    calendar.time = date

    return calendar.get(Calendar.YEAR).toString()
}

fun getDateOfBirth(date: Date?): String {
    return if (date != null) {
        val calendar = Calendar.getInstance()
        calendar.time = date

        val dayOfMonth =
            if (calendar.get(Calendar.DAY_OF_MONTH) < 10)
                "0${calendar.get(Calendar.DAY_OF_MONTH)}"
            else
                "${calendar.get(Calendar.DAY_OF_MONTH)}"
        val month =
            if (calendar.get(Calendar.MONTH) + 1 < 10)
                "0${calendar.get(Calendar.MONTH) + 1}"
            else
                "${calendar.get(Calendar.MONTH) + 1}"
        val year = "${calendar.get(Calendar.YEAR)}"

        "$dayOfMonth.$month.$year"
    } else {
        ""
    }
}