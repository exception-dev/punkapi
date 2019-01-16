package com.ex.punkapi.model


class MethodModel{
    var mashTemp:ArrayList<MethodItemModel> = ArrayList()
    var fermentation:MethodItemModel = MethodItemModel()
    var twist:String = ""
    override fun toString(): String {
        return "MethodModel(mashTemp=$mashTemp, fermentation=$fermentation, twist='$twist')"
    }


}