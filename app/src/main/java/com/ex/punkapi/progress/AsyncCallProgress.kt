package com.ex.punkapi.progress

import android.content.Context
import java.util.concurrent.atomic.AtomicInteger

class AsyncCallProgress(private val dialogResId: Int) {

    private val count = AtomicInteger(0)
    private var dialog: AsyncCallProgressDialog? = null

    fun show(context: Context) {
        if (count.incrementAndGet() == 1) {
            dialog = AsyncCallProgressDialog(context)
            dialog?.setLayout(dialogResId)
            dialog?.show()
        }
    }

    fun dismiss() {

        if (count.decrementAndGet() == 0)
            dialog!!.dismiss()
    }

    fun cancel() {
        count.set(0)

        if (dialog != null && dialog!!.isShowing())
            dialog!!.dismiss()
    }

    companion object {

        private var instance: AsyncCallProgress? = null

        fun instance(dialogResId: Int): AsyncCallProgress {
            if (instance == null)
                instance = AsyncCallProgress(dialogResId)

            return instance!!
        }
    }

}