package com.das.lookupvpn

import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("api/iphone")
    fun getServerData(): Call<String>;
}