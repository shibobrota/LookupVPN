package com.das.lookupvpn

import android.content.Context
import android.util.Log
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

class LookupVPN {

    val TAG = "LookupVPN:"

    var context: Context

    constructor(context: Context){
        this.context = context
    }

    fun fetchServerData(onResponse: ((str: String)->Unit)? = null, onError: ((err: String)->Unit)? = null){
        val serverUrl = "https://www.vpngate.net/api/iphone"
        var reqQueue = Volley.newRequestQueue(context)
        var strRequest = StringRequest(
            Request.Method.GET,
            serverUrl,
            {
                if (onResponse != null){
                    onResponse(it)
                }
            },
            {
                if (onError != null){
                    onError(it.toString())
                }
            }
        )
        strRequest.setRetryPolicy(DefaultRetryPolicy(
            10000,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ))
        reqQueue.add(strRequest)
        Log.d(TAG, "Working!!")
    }
}