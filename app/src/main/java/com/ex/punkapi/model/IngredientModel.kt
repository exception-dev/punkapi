package com.ex.punkapi.model

import io.realm.RealmList
import io.realm.RealmObject


open class IngredientModel:RealmObject(){
    var malt:RealmList<IngredientItemModel> = RealmList()
    var hope:RealmList<IngredientItemModel> = RealmList()
    var yeast:String = ""
    override fun toString(): String {
        return "IngredientModel(malt=$malt, hope=$hope, yeast='$yeast')"
    }

}