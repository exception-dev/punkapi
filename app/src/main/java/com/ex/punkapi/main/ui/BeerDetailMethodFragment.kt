package com.ex.punkapi.main.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import android.view.View
import com.ex.punkapi.R
import kotlinx.android.synthetic.main.fragment_beer_detail_method.*

class BeerDetailMethodFragment : BeerDetailBaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_beer_detail_method, container, false)

        }

        return rootView
    }


    override fun bindData() {
        if(beerModel?.method?.mashTemp != null){
            var sb = StringBuilder()

            for(mash in beerModel?.method?.mashTemp!!){
                if(sb.isNotEmpty()){
                    sb.append("\n")
                }
                sb.append("• ")
                sb.append(mash.temp?.value)
                sb.append(mash.temp?.unit)

                if(mash.duration != null){
                    sb.append("(${mash.duration})")
                }
            }
            txtMash.text = sb.toString()
        }

        if(beerModel?.method?.fermentation != null){
            var sb = StringBuilder()

            val fermentation = beerModel?.method?.fermentation!!
            sb.append("• ")
            sb.append(fermentation.temp?.value)
            sb.append(fermentation!!.temp?.unit)


            txtMash.text = sb.toString()
        }




        txtTwist.text = beerModel?.method?.twist
    }


}