package com.ex.punkapi.util

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.widget.Toast

object AlertUtils {

    internal var toast: Toast? = null

    fun toastShort(context: Context, msg: String) {
        toast(context, msg, Toast.LENGTH_SHORT)
    }

    fun toastShort(context: Context, msgRes: Int) {
        toastShort(context, context.getString(msgRes))
    }

    fun toastLong(context: Context, msg: String) {
        toast(context, msg, Toast.LENGTH_LONG)
    }

    fun toastLong(context: Context, msgRes: Int) {
        toastLong(context, context.getString(msgRes))
    }

    private fun toast(context: Context, msg: String, time: Int) {
        if (toast != null)
            toast!!.cancel()

        toast = Toast.makeText(context, msg, time)
        toast!!.show()
    }


    fun showConfirm(
        context: Context,
        message: String,
        positiveListener: DialogInterface.OnClickListener,
        negativeListener: DialogInterface.OnClickListener?
    ) {
        AlertDialog.Builder(context).setMessage(message)
            .setPositiveButton(android.R.string.ok, positiveListener)
            .setNegativeButton(android.R.string.cancel, negativeListener).setCancelable(false).create().show()
    }

    fun showConfirm(
        context: Context,
        title: String,
        message: String,
        positiveListener: DialogInterface.OnClickListener,
        negativeListener: DialogInterface.OnClickListener
    ) {
        AlertDialog.Builder(context).setTitle(title).setMessage(message)
            .setPositiveButton(android.R.string.ok, positiveListener)
            .setNegativeButton(android.R.string.cancel, negativeListener).setCancelable(false).create().show()
    }

    fun showAlert(context: Context, message: String, positiveListener: DialogInterface.OnClickListener?) {
        AlertDialog.Builder(context).setMessage(message)
            .setPositiveButton(android.R.string.ok, positiveListener).setCancelable(false).create().show()
    }


}