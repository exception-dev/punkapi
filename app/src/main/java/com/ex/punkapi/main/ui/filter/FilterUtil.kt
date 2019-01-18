package com.ex.punkapi.main.ui.filter

import com.ex.punkapi.R

object FilterUtil{
    enum class ABV_FILTER(val strResId: Int) {
        NONE(R.string.filter_none), WEAKLY(R.string.weakly), NORMAL(R.string.normal), STRONGLY(R.string.strongly)
    }

    enum class IBU_FILTER(val strResId: Int)  {
        NONE(R.string.filter_none), WEAKLY(R.string.weakly), NORMAL(R.string.normal), STRONGLY(R.string.strongly)
    }

    enum class EBC_FILTER(val strResId: Int)  {
        NONE(R.string.filter_none), LIGHTLY(R.string.lighty), NORMAL(R.string.normal), THICK(R.string.thick)
    }

    fun getAbvParam(abv: ABV_FILTER): MutableMap<String, Any>{
        val param = mutableMapOf<String, Any>()

        when(abv){
            ABV_FILTER.WEAKLY -> {
                param["abv_gt"] = 0
                param["abv_lt"] = 4
            }

            ABV_FILTER.NORMAL -> {
                param["abv_gt"] = 4
                param["abv_lt"] = 7
            }

            ABV_FILTER.STRONGLY -> {
                param["abv_gt"] = 7
                param["abv_lt"] = 10
            }
        }

        return param
    }

    fun getIbuParam(ibu: IBU_FILTER): MutableMap<String, Any>{
        val param = mutableMapOf<String, Any>()

        when(ibu){
            IBU_FILTER.WEAKLY -> {
                param["ibu_gt"] = 0
                param["ibu_lt"] = 30
            }

            IBU_FILTER.NORMAL -> {
                param["ibu_gt"] = 30
                param["ibu_lt"] = 60
            }

            IBU_FILTER.STRONGLY -> {
                param["ibu_gt"] = 60
                param["ibu_lt"] = 100
            }
        }

        return param
    }

    fun getEbcParam(ebc: EBC_FILTER): MutableMap<String, Any>{
        val param = mutableMapOf<String, Any>()

        when(ebc){
            IBU_FILTER.WEAKLY -> {
                param["ebc_gt"] = 0
                param["ebc_lt"] = 30
            }

            IBU_FILTER.NORMAL -> {
                param["ebc_gt"] = 30
                param["ebc_lt"] = 60
            }

            IBU_FILTER.STRONGLY -> {
                param["ebc_gt"] = 60
                param["ebc_lt"] = 100
            }
        }

        return param
    }
}