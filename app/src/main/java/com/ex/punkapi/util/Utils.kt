package com.ex.punkapi.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.media.ExifInterface
import android.net.Uri
import android.provider.MediaStore
import android.provider.Settings
import android.telephony.TelephonyManager
import android.util.Log
import android.util.TypedValue
import android.view.View
import kotlinx.android.synthetic.main.activity_purchase.*
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.text.NumberFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern

object Utils {


    fun getCurrency(value: Long): String{
        val currencyFormat = NumberFormat.getCurrencyInstance();
        return currencyFormat.format(value)
    }

    fun toDateString(dateString: String, fromFormat: String, toFormat: String): String {
        var result = ""
        val df = SimpleDateFormat(fromFormat, Locale.getDefault())
        val df2 = SimpleDateFormat(toFormat, Locale.getDefault())
        try {
            result = df2.format(df.parse(dateString))
        } catch (e: ParseException) {
            return dateString
        }

        return result
    }

}