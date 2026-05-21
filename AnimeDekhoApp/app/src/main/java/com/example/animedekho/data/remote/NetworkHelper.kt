package com.example.animedekho.data.remote

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import com.example.animedekho.di.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class NetworkHelper @Inject constructor(@ApplicationContext private val context: Context) {

    @SuppressLint("ServiceCast")
    fun isNetworkConnected(): Boolean {
        val cm =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return cm.activeNetworkInfo?.isConnected == true
    }
}
