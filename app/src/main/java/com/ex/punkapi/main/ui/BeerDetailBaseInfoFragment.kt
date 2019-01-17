package com.ex.punkapi.main.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import android.view.View
import com.ex.punkapi.R
import com.ex.punkapi.base.ui.BaseFragment
import com.ex.punkapi.common.Constants
import com.ex.punkapi.model.BeerModel
import kotlinx.android.synthetic.main.fragment_beer_detail_base_info.*

class BeerDetailBaseInfoFragment : BaseFragment() {

    private var beerId:Long = 0
    private var beerModel: BeerModel? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_beer_detail_base_info, container, false)

        }

        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        getArgumentData()
        bindData()
    }

    private fun getArgumentData() {
        if (arguments != null) {
            beerId = arguments!!.getLong(Constants.BEER_ID)

            val realm = app.getRealm()
            beerModel = realm.where(BeerModel::class.java).equalTo("id", beerId).findFirst()

        }
    }

    private fun bindData() {
        txtTagline.text = beerModel?.tagline
        txtBrewed.text = beerModel?.firstBrewed
        txtDescription.text = beerModel?.description
        txtFoodPairing.text = beerModel?.foodPairing?.joinToString ("\n")
        txtBrewersTip.text = beerModel?.brewersTips
        txtContribute.text = beerModel?.contributedBy
    }


}