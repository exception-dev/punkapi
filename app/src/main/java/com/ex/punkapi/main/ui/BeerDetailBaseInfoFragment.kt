package com.ex.punkapi.main.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import android.view.View
import com.ex.punkapi.R
import com.ex.punkapi.util.Utils
import kotlinx.android.synthetic.main.fragment_beer_detail_base_info.*

class BeerDetailBaseInfoFragment : BeerDetailBaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_beer_detail_base_info, container, false)

        }

        return rootView
    }


    override fun bindData() {
        txtTagline.text = beerModel?.tagline
        txtBrewed.text = Utils.toDateString(beerModel?.firstBrewed, "mm-yyyyy", "yyyy-mm")
        txtDescription.text = beerModel?.description
        txtFoodPairing.text = beerModel?.foodPairing?.joinToString ("\n")
        txtBrewersTip.text = beerModel?.brewersTips
        txtContribute.text = beerModel?.contributedBy
    }


}