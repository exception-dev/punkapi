package com.ex.punkapi.model

import io.realm.RealmList
import io.realm.RealmObject


open class MethodModel: RealmObject(){
    var mashTemp:RealmList<MethodItemModel> = RealmList()
    var fermentation:MethodItemModel? = MethodItemModel()
    var twist:String? = ""
    override fun toString(): String {
        return "MethodModel(mashTemp=$mashTemp, fermentation=$fermentation, twist='$twist')"
    }


}