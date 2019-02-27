package com.example.fruitapp

import android.app.Application
import com.google.gson.Gson
import okhttp3.OkHttpClient

class FruitApplication: Application() {

    val okHttpClient = OkHttpClient()
    val gson = Gson()

}