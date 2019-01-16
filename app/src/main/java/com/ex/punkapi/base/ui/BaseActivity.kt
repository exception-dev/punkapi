package com.ex.punkapi.base.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.ex.punkapi.base.BaseApplication
import com.ex.punkapi.network.ApiService
import com.ex.punkapi.network.api.ApiFactory
import com.ex.punkapi.network.callback.NetWorkCallback
import com.ex.punkapi.network.util.CallQueue
import retrofit2.Call

open class BaseActivity : AppCompatActivity() {


    private lateinit var callQueue: CallQueue
    protected lateinit var apiService: ApiService
    protected lateinit var app: BaseApplication

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        app = application as BaseApplication
        callQueue = CallQueue(this)
        apiService = ApiFactory.createApiService(this)
    }

    override fun onDestroy() {
        callQueue.cancelAll()
        super.onDestroy()
    }

    protected fun <T> requestCall(call: Call<T>, callback: NetWorkCallback<T>) {
        requestCall(call, callback, true)
    }

    protected fun <T> requestCall(call: Call<T>, callback: NetWorkCallback<T>, isShowProgress: Boolean) {
        callQueue.add(call, callback, this, isShowProgress)
    }
}