package com.ex.punkapi.network.callback

import android.content.Context
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

open class NetWorkCallback<T>(private val context: Context) : Callback<T> {


    override fun onResponse(call: Call<T>, response: Response<T>) {

        if (check(response)) {

            onSuccess(call, response, response.body() as T)

        } else {
            onFail(call, response)
        }

        onComplete(call, response)
    }

    override fun onFailure(call: Call<T>, t: Throwable) {
        println("********onFailure************")
        println("t.toString() : " + t.toString())

    }

    protected open fun onSuccess(call: Call<T>, response: Response<T>, data: T) {

    }

    protected open fun onFail(call: Call<T>, response: Response<T>) {


    }


    protected open fun onComplete(call: Call<T>, response: Response<T>) {

    }

    protected open fun check(response: Response<*>): Boolean {

        return response.isSuccessful
    }
}