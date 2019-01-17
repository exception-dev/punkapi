package com.ex.punkapi.model

import io.realm.RealmObject


open class IngredientItemModel:RealmObject(){
    var name:String = ""
    var amount:ValueModel? = ValueModel()
    var add:String = ""
    var attribute = ""
    override fun toString(): String {
        return "IngredientItemModel(name='$name', amount=$amount, add='$add', attribute='$attribute')"
    }

}