package com.ex.punkapi.util

import java.lang.NullPointerException
import java.text.NumberFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object Utils {


    fun getCurrency(value: Long): String{
        val currencyFormat = NumberFormat.getCurrencyInstance();
        return currencyFormat.format(value)
    }

    fun toDateString(dateString: String?, fromFormat: String, toFormat: String): String {
        var result = ""
        val df = SimpleDateFormat(fromFormat, Locale.getDefault())
        val df2 = SimpleDateFormat(toFormat, Locale.getDefault())
        try {
            result = df2.format(df.parse(dateString))
        } catch (e: ParseException) {
            return dateString!!
        } catch (e: NullPointerException){
            return ""
        }

        return result
    }

}