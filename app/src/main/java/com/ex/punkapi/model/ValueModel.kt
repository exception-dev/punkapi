package com.ex.punkapi.model

class ValueModel{
    var value: Number = 0
    var unit: String = ""
    override fun toString(): String {
        return "ValueModel(value=$value, unit='$unit')"
    }

}