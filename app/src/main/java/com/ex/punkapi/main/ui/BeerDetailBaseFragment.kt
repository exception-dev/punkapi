package com.ex.punkapi.main.ui

import android.os.Bundle
import com.ex.punkapi.base.ui.BaseFragment
import com.ex.punkapi.common.Constants
import com.ex.punkapi.model.BeerModel

open class BeerDetailBaseFragment : BaseFragment() {

    protected var beerId:Long = 0
    protected var beerModel: BeerModel? = null


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

    protected open fun bindData() {

    }


}