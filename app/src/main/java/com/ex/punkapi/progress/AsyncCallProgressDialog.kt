package com.ex.punkapi.progress


import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window

class AsyncCallProgressDialog(context: Context) : Dialog(context) {
    private var layoutResID = 0

    init {

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        setCancelable(false)
    }

    fun setLayout(layoutResID: Int) {
        this.layoutResID = layoutResID
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutResID)
    }

}