package com.ex.punkapi.main.ui

import android.os.Bundle
import com.ex.punkapi.R
import com.ex.punkapi.base.glide.GlideApp
import com.ex.punkapi.base.ui.BaseTabActivity
import com.ex.punkapi.common.Constants
import com.ex.punkapi.model.BeerModel
import android.support.design.widget.AppBarLayout
import kotlinx.android.synthetic.main.activity_beer_detail.*


class BeerDetailActivity : BaseTabActivity() {

    private var beerId:Long = 0
    private var beerModel: BeerModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_beer_detail)

        getIntentData()
        init()
        regEvent()
    }

    private fun getIntentData(){
        beerId = intent.getLongExtra(Constants.BEER_ID, 0);


    }

    private fun init() {
        val realm = app.getRealm()
        beerModel = realm.where(BeerModel::class.java).equalTo("id", beerId).findFirst()

        if(beerModel == null){
            finish()
        }

        bindData()
        initTab()
    }

    override fun initTab() {
        super.initTab()
        makeMenuTab(getTabView(getString(R.string.base_info)), BeerDetailBaseInfoFragment::class.java!!, getBundle(), null)
        makeMenuTab(getTabView(getString(R.string.method)), BeerDetailBaseInfoFragment::class.java!!, getBundle(), null)
        makeMenuTab(getTabView(getString(R.string.ingredient)), BeerDetailBaseInfoFragment::class.java!!, getBundle(), null)

    }

    private fun getBundle() : Bundle{
        val bundle = Bundle()
        bundle.putLong(Constants.BEER_ID, beerId)
        return bundle
    }

    private fun regEvent() {
        toolbar?.setNavigationOnClickListener{
            finish()
        }

        appBarLayout.addOnOffsetChangedListener(object : AppBarLayout.OnOffsetChangedListener {
            var isShow = true
            var scrollRange = -1

            override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {

                if (scrollRange == -1) {
                    scrollRange = appBarLayout.totalScrollRange
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbarLayout.title = beerModel?.name
                    isShow = true
                } else if (isShow) {
                    collapsingToolbarLayout.title = ""
                    isShow = false
                }

            }
        })

    }

    private fun bindData(){
        GlideApp.with(this)
            .load(beerModel?.imageUrl)
            .into(imgBeer)

        txtName.text = beerModel?.name

        txtAbv.text = beerModel?.abv
        txtIbu.text = beerModel?.ibu
        txtSrm.text = beerModel?.srm
        txtEbc.text = beerModel?.ebc
        txtPh.text = beerModel?.ph

    }

}
