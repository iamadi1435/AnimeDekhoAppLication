package com.example.animedekho.data.remote

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager

class NetworkHelper(private val context: Context) {

    @SuppressLint("ServiceCast")
    fun isNetworkConnected(): Boolean {
        val cm =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return cm.activeNetworkInfo?.isConnected == true
    }
}
