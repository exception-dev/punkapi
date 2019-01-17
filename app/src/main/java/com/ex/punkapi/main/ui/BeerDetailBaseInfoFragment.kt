package com.ex.punkapi.main.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import android.view.View
import com.ex.punkapi.R
import com.ex.punkapi.base.ui.BaseFragment

class BeerDetailBaseInfoFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_beer_detail_base_info, container, false)

        }

        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        init()
        getArgumentData()
    }



    private fun init(){

    }



    private fun getArgumentData() {
        if (arguments != null) {

        }

    }


}