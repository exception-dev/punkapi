package com.ex.punkapi.manager

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Message
import com.ex.punkapi.common.Constants
import com.ex.punkapi.manager.callback.OnPurchaseCompleteListener


class PurchaseCompleteManager(private val context: Context) {

    companion object {
        private val PURCHASE_MESSAGE = 1000

    }

    private val lockObject = Any()

    private val callbacks = mutableListOf<OnPurchaseCompleteListener>()

    private val callbackHandler = object : Handler() {

        override fun handleMessage(msg: Message) {
            when (msg.what) {

                PURCHASE_MESSAGE -> {
                    (msg.obj as OnPurchaseCompleteListener).onComplete(msg.getData().getString(Constants.Key.USER_NAME))
                }

            }
        }
    }

    fun addCallback(callback: OnPurchaseCompleteListener) {
        synchronized(lockObject) {
            callbacks.add(callback)
        }
    }


    fun removeCallback(callback: OnPurchaseCompleteListener) {
        synchronized(lockObject) {
            callbacks.remove(callback)
        }
    }


    fun purcharseComplete(name: String) {
        notiPurcharseComplete(name)
    }


    private fun notiPurcharseComplete(name: String) {
        for (callback in callbacks) {
            notiChangeCart(name, PURCHASE_MESSAGE, callback)
        }

    }

    private fun notiChangeCart(name: String, what: Int, callback: OnPurchaseCompleteListener) {

        val bundle = Bundle()
        bundle.putString(Constants.Key.USER_NAME, name)
        val msg = callbackHandler.obtainMessage(what, callback)
        msg.setData(bundle)
        callbackHandler.sendMessage(msg)
    }


}