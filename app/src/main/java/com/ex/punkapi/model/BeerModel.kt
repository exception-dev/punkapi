package com.ex.punkapi.model


class BeerModel{


    var id:Long = 0
    var name: String = ""
    var tagline: String = ""

    var firstBrewed: String = ""
    var description: String = ""
    var imageUrl: String = ""
    var abv: Number = 0
    var ibu: Number = 0
    var targetFg: Number = 0
    var targetOg: Number = 0
    var ebc: Number = 0
    var srm: Number = 0
    var ph: Number = 0
    var attenustionLevel: Number = 0
    var volume: ValueModel = ValueModel()
    var boilVolume: ValueModel = ValueModel()
    var method : MethodModel = MethodModel()
    var foodPairing: ArrayList<String> = ArrayList()
    var brewersTips: String = ""
    var contributedBy : String = ""
    override fun toString(): String {
        return "BeerModel(id=$id, name='$name', tagline='$tagline', firstBrewed='$firstBrewed', description='$description', imageUrl='$imageUrl', abv=$abv, ibu=$ibu, targetFg=$targetFg, targetOg=$targetOg, ebc=$ebc, srm=$srm, ph=$ph, attenustionLevel=$attenustionLevel, volume=$volume, boilVolume=$boilVolume, method=$method, foodPairing=$foodPairing, brewersTips='$brewersTips', contributedBy='$contributedBy')"
    }


}