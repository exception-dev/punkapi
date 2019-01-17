package com.ex.punkapi.model

import io.realm.RealmObject

open class ValueModel:RealmObject(){
    var value: String = ""
    var unit: String = ""
    override fun toString(): String {
        return "ValueModel(value=$value, unit='$unit')"
    }

}