package com.ex.punkapi.model

import io.realm.RealmList
import io.realm.RealmObject


open class IngredientModel:RealmObject(){
    var malt:RealmList<IngredientItemModel> = RealmList()
    var hops:RealmList<IngredientItemModel> = RealmList()
    var yeast:String = ""
    override fun toString(): String {
        return "IngredientModel(malt=$malt, hops=$hops, yeast='$yeast')"
    }

}