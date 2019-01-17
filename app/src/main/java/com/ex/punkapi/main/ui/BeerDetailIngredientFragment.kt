package com.ex.punkapi.main.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import android.view.View
import com.ex.punkapi.R
import kotlinx.android.synthetic.main.fragment_beer_detail_base_info.*
import kotlinx.android.synthetic.main.fragment_beer_detail_ingredient.*
import kotlinx.android.synthetic.main.fragment_beer_detail_method.*

class BeerDetailIngredientFragment : BeerDetailBaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_beer_detail_ingredient, container, false)

        }

        return rootView
    }


    override fun bindData() {
        if(beerModel?.ingredients?.malt != null){
            var sb = StringBuilder()

            for(malt in beerModel?.ingredients?.malt!!){
                if(sb.isNotEmpty()){
                    sb.append("\n")
                }
                sb.append("• ")
                sb.append(malt.name)
                if(malt.amount != null){
                    sb.append(" (${malt.amount?.value} ${malt.amount?.unit})")
                }
            }
            txtMalt.text = sb.toString()
        }

        if(beerModel?.ingredients?.hops != null){
            var sb = StringBuilder()

            for(hops in beerModel?.ingredients?.hops!!){
                if(sb.isNotEmpty()){
                    sb.append("\n")
                }
                sb.append("• ")
                sb.append(hops.name)
                if(hops.amount != null){
                    sb.append(" (${hops.amount?.value} ${hops.amount?.unit})")
                }

                if(!hops.add.isEmpty()){
                    sb.append(" (${hops.add} - ${hops.attribute})")
                }
            }



            txtHops.text = sb.toString()
        }




        txtYeast.text = beerModel?.ingredients?.yeast
    }


}