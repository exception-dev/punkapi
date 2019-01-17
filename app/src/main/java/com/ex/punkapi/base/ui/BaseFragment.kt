package com.ex.punkapi.base.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import android.view.ViewGroup
import com.ex.punkapi.base.BaseApplication


open class BaseFragment : Fragment() {

    protected var callbackListener: BaseTabActivity.OnActivityCallbackListener? = null
    protected lateinit var tabFragmentCallbackListener: OnTabFragmentCallbackListener
    protected var fragmentCallbackListener: OnFragmentCallbackListener? = null
    protected lateinit var app: BaseApplication

    protected var rootView: View? = null

    protected var backStackEntryCount = 0



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        backStackEntryCount = fragmentManager!!.backStackEntryCount
    }



    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        app = context!!.applicationContext as BaseApplication
    }





    override fun onDestroyView() {
        if (rootView != null && rootView!!.getParent() != null) {
            (rootView!!.getParent() as ViewGroup).removeView(rootView)
        }
        super.onDestroyView()
    }


    fun setOnCallbackListener(callbackListener: BaseTabActivity.OnActivityCallbackListener) {
        this.callbackListener = callbackListener
    }

    fun setOnTabFragmentCallbackListener(callbackListener: OnTabFragmentCallbackListener) {
        this.tabFragmentCallbackListener = callbackListener
    }

    interface OnTabFragmentCallbackListener {
        fun call(`object`: Any)
    }

    fun setOnFragmentCallbackListener(listener: OnFragmentCallbackListener) {
        this.fragmentCallbackListener = listener
    }

    interface OnFragmentCallbackListener {
        fun callback(`object`: Any?)
    }


}