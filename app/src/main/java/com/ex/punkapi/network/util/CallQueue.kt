package com.ex.punkapi.network.util

import android.app.Activity
import android.content.Context
import com.ex.punkapi.progress.AsyncCallProgress
import com.ex.punkapi.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList

class CallQueue(context: Context) {

    private val callList = ArrayList<Call<*>>()

    private val progress: AsyncCallProgress


    init {

        this.progress = AsyncCallProgress.instance(R.layout.app_progress)
    }

    fun <T> add(call: Call<T>, callback: Callback<T>, context: Context) {
        add(call, callback, context, false)
    }


    fun <T> add(call: Call<T>, callback: Callback<T>, context: Context, isShowProgress: Boolean) {

        callList.add(call)

        if (isShowProgress && !(context as Activity).isFinishing) {
            progress.show(context)
        }

        call.enqueue(object : Callback<T> {

            override fun onResponse(call: Call<T>, response: Response<T>) {

                if (call.isCanceled) {
                    return
                }

                if (isShowProgress) {
                    progress.dismiss()
                }

                callList.remove(call)


                callback.onResponse(call, response)
            }

            override fun onFailure(call: Call<T>, t: Throwable) {

                if (call.isCanceled) {
                    return
                }
                if (isShowProgress) {
                    progress.dismiss()
                }
                callList.remove(call)
                callback.onFailure(call, t)
            }
        })


    }


    fun cancelAll() {
        progress.cancel()
        val iterator = callList.iterator()
        while (iterator.hasNext()) {
            val call = iterator.next()
            call.cancel()
            iterator.remove()
        }
    }
}