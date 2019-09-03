package com.yogi.examplemvvm.utils

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.os.Build
import android.text.format.DateFormat
import android.text.format.DateUtils
import com.yogi.examplemvvm.utils.DateHelper.ABBR_DAY
import com.yogi.examplemvvm.utils.DateHelper.ABBR_HOUR
import com.yogi.examplemvvm.utils.DateHelper.ABBR_MINUTE
import com.yogi.examplemvvm.utils.DateHelper.ABBR_SECOND
import com.yogi.examplemvvm.utils.DateHelper.ABBR_WEEK
import com.yogi.examplemvvm.utils.DateHelper.ABBR_YEAR
import com.yogi.examplemvvm.utils.DateHelper.DATE_DEFAULT_WITH_TIME
import com.yogi.examplemvvm.utils.DateHelper.LOCALE
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.max

/**
 * Created by oohyugi on 2019-09-03.
 * github: https://github.com/oohyugi
 */


object DateHelper {
    const val DATE_DEFAULT = "yyyy-MM-dd"

    const val DATE_DEFAULT_WITH_TIME = "yyyy-MM-dd HH:mm"
    const val DATE_DDMMYYYY = "dd-MM-yyyy"
    const val DATE_EEDDMMMYYYY = "EEEE, dd MMMM yyyy"
    const val DATE_DDMMMMYYYY = "dd MMMM yyyy"
    const val DATE_YYYY_MM_DD = "yyyy/MM/dd"
    const val DATE_YYYY_MM_DD_WITH_TIME = "yyyy/MM/dd HH:mm"
    const val DATE_SERVER = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
    const val LOCALE = "ID"

    const val ABBR_YEAR = " year ago"
    const val ABBR_WEEK = " week ago"
    const val ABBR_DAY = " day ago"
    const val ABBR_HOUR = " hour ago"
    const val ABBR_MINUTE = " minutes ago"
    const val ABBR_SECOND = " second ago"
}


@SuppressLint("SimpleDateFormat")
fun String.changeFormatDate(old_format: String, formatDate: String): String {
    return try {
        val sdf = SimpleDateFormat(old_format)
        val date: Date?
        date = sdf.parse(this)
        val id = Locale("in", LOCALE)
        val format = SimpleDateFormat(formatDate, id)
        format.format(date!!)
    } catch (e: ParseException) {
        this
    }

}

fun Date.dateParseToString(old_format: String): String {
    val id = Locale("in", LOCALE)
    val format = SimpleDateFormat(old_format, id)
    return format.format(this)
}

fun String.dateParseToDate(old_format: String): Date? {
    return try {
        val id = Locale("in", LOCALE)
        val sdf = SimpleDateFormat(old_format, id)
        sdf.parse(this)
    } catch (e: Exception) {
        null
    }

}

@SuppressLint("SimpleDateFormat")
fun Long.convertToTimeString(format: String): String {

    return try {
        val dateLong = Date(this * 1000L)
        val date = DateFormat.format(format, dateLong).toString()

        val formatter = SimpleDateFormat(format)
        val value = formatter.parse(date)

        val dateFormatter = SimpleDateFormat(format)
        dateFormatter.timeZone = TimeZone.getDefault()
        dateFormatter.format(value)
    } catch (e: java.lang.Exception) {
        "$this"
    }
}

@TargetApi(Build.VERSION_CODES.N)
@SuppressLint("SimpleDateFormat")
fun String.getAbbreviatedTimeSpan(): String {
    val timeMillis: Long
    var span: Long = 0
    val mCalendar = GregorianCalendar()
    try {
        val sdf = SimpleDateFormat(DATE_DEFAULT_WITH_TIME)
        val sdf2 = SimpleDateFormat(DATE_DEFAULT_WITH_TIME)
        var mDate: Date? = null
        try {
            mDate = sdf.parse(this)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        timeMillis = mDate!!.time
        sdf2.timeZone = mCalendar.timeZone
        val sDateInUTC = sdf2.format(Date())
        val mDateNow = sdf.parse(sDateInUTC)
        val timeNow = mDateNow.time
        //span = Math.max(System.currentTimeMillis() - timeMillis, 0);
        span = max(timeNow - timeMillis, 0)
        if (span >= DateUtils.YEAR_IN_MILLIS) {
            return (span / DateUtils.YEAR_IN_MILLIS).toString() + ABBR_YEAR
        }
        if (span >= DateUtils.WEEK_IN_MILLIS) {
            return (span / DateUtils.WEEK_IN_MILLIS).toString() + ABBR_WEEK
        }
        if (span >= DateUtils.DAY_IN_MILLIS) {
            return (span / DateUtils.DAY_IN_MILLIS).toString() + ABBR_DAY
        }
        if (span >= DateUtils.HOUR_IN_MILLIS) {
            return (span / DateUtils.HOUR_IN_MILLIS).toString() + ABBR_HOUR
        }
        if (span >= DateUtils.MINUTE_IN_MILLIS) {
            return (span / DateUtils.MINUTE_IN_MILLIS).toString() + ABBR_MINUTE
        }
        if (span >= DateUtils.SECOND_IN_MILLIS) {
            return (span / DateUtils.SECOND_IN_MILLIS).toString() + ABBR_SECOND
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }

    return (span / DateUtils.MINUTE_IN_MILLIS).toString() + ABBR_MINUTE
}



