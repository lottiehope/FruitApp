package com.example.fruitapp

import com.google.gson.Gson
import okhttp3.*
import java.io.IOException

class FruitParser(private val client: OkHttpClient, private val gson: Gson) {

    private val fruitDataRequest = Request.Builder()
        .url("https://raw.githubusercontent.com/fmtvp/recruit-test-data/master/data.json")
        .addHeader("accept", "application/json")
        .build()

    private fun parseFruitDataIntoFruitCollection(data: String): FruitCollection {
        return gson.fromJson(data, FruitCollection::class.java)
    }

    fun requestFruitData(timeService: TimeService, successCallback: (FruitCollection, Int) -> Unit, failureCallback: (IOException) -> Unit) {
        val startTime = timeService.getTimeNow()

        client.newCall(fruitDataRequest).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                failureCallback(e)
            }

            override fun onResponse(call: Call, response: Response) {
                val endTime = timeService.getTimeNow()
                val timePassed = timeService.getTimePassedInMillis(startTime, endTime)
                response.body()?.string()?.let {
                    val data = parseFruitDataIntoFruitCollection(it)
                    successCallback(data, timePassed)
                }
            }

        })
    }

}