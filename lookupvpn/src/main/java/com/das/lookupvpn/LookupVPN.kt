package com.das.lookupvpn

import android.content.Context
import android.graphics.Movie
import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Converter
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory


class LookupVPN {

    val TAG = "LookupVPN:"

    var context: Context

    constructor(context: Context){
        this.context = context
    }

    fun fetchServerData(onResponse: ((str: String)->Unit)? = null, onError: ((err: String)->Unit)? = null){
        val serverUrl = "https://www.vpngate.net/"
        val retrofit = Retrofit.Builder()
            .baseUrl(serverUrl)
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
        val apiService: ApiService = retrofit.create(ApiService::class.java)
        val call: Call<String> = apiService.getServerData()
        call.enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (onResponse != null) onResponse(response.body().toString())
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                if (onError != null) onError(t.stackTraceToString())
            }
        })
        Log.d(TAG, "Working!!")
    }
}