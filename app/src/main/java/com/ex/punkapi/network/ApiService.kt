package com.ex.punkapi.network

import com.ex.punkapi.model.BeerModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap


interface ApiService {

    @GET("beers")
    fun beers(@QueryMap params: HashMap<String, Any>): Call<ArrayList<BeerModel>>

    @GET("beers/{id}")
    fun playerInfo(@Path("id") playerId: String): Call<*>

}