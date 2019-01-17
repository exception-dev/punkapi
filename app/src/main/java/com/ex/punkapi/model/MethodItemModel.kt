package com.ex.punkapi.model

import io.realm.RealmObject


open class MethodItemModel: RealmObject(){
    var temp:ValueModel? = ValueModel()
    var duration:String? = ""

    override fun toString(): String {
        return "MethodItemModel(temp=$temp, duration=$duration)"
    }


}