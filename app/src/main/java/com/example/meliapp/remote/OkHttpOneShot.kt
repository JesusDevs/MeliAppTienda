package com.example.meliapp.remote

import android.util.Log
import okhttp3.OkHttpClient

object OkHttpOneShot: OkHttpClient() {
    val  client = OkHttpClient.Builder().build()
    override fun newBuilder(): Builder {
        return super.newBuilder()
    }
    override fun hashCode(): Int {
        Log.d("RetrofitApiBuilder", "instancia: ${super.hashCode()}")
        return super.hashCode()
    }
}

