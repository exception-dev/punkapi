package com.ex.punkapi.model

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey


open class BeerModel: RealmObject(){
    @PrimaryKey
    var id:Long = 0
    var name: String = ""
    var tagline: String = ""

    var firstBrewed: String = ""
    var description: String = ""
    var imageUrl: String = ""
    var abv: String? = ""
    var ibu: String? = ""
    var targetFg: String? = ""
    var targetOg: String? = ""
    var ebc: String? = ""
    var srm: String? = ""
    var ph: String? = ""
    var attenustionLevel: Float = 0f
    var volume: ValueModel? = ValueModel()
    var boilVolume: ValueModel? = ValueModel()
    var method : MethodModel? = MethodModel()
    var ingredients: IngredientModel? = IngredientModel()
    var foodPairing: RealmList<String>? = RealmList()
    var brewersTips: String = ""
    var contributedBy : String = ""
    override fun toString(): String {
        return "BeerModel(id=$id, name='$name', tagline='$tagline', firstBrewed='$firstBrewed', description='$description', imageUrl='$imageUrl', abv=$abv, ibu=$ibu, targetFg=$targetFg, targetOg=$targetOg, ebc=$ebc, srm=$srm, ph=$ph, attenustionLevel=$attenustionLevel, volume=$volume, boilVolume=$boilVolume, method=$method, ingredients=$ingredients, foodPairing=$foodPairing, brewersTips='$brewersTips', contributedBy='$contributedBy')"
    }


}