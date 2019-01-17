package com.ex.punkapi.widget

import android.content.Context
import android.view.MotionEvent
import android.support.v4.view.ViewPager
import android.util.AttributeSet


class BaseViewPager : ViewPager {
    private var flingAble = true

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}
    constructor(context: Context) : super(context) {}

    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {


        try {
            return if (!flingAble) {
                false
            } else super.onInterceptTouchEvent(event)

        } catch (e: Exception) {
            return false
        }

    }


    override fun onTouchEvent(arg0: MotionEvent): Boolean {
        return if (!flingAble) {
            false
        } else super.onTouchEvent(arg0)
    }

    fun setFlingAble(flag: Boolean) {
        flingAble = flag
    }
}