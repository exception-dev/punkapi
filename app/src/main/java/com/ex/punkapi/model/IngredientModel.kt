package com.ex.punkapi.model


class IngredientModel{
    var malt:ArrayList<IngredientItemModel> = ArrayList()
    var hope:ArrayList<IngredientItemModel> = ArrayList()
    var yeast:String = ""
    override fun toString(): String {
        return "IngredientModel(malt=$malt, hope=$hope, yeast='$yeast')"
    }

}