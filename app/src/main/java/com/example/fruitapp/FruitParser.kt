package com.example.fruitapp

import okhttp3.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException

class FruitParser(private val client: OkHttpClient) {

    fun parseFruitDataIntoFruitInfo(data: JSONArray): MutableList<FruitInfo> {
        val fruitList = mutableListOf<FruitInfo>()

        for(index in 0..(data.length() - 1)) {
            val nextFruit = data.getJSONObject(index)
            val fruit = FruitInfo(
                nextFruit.getString("type"),
                nextFruit.getInt("price"),
                nextFruit.getInt("weight"))
            fruitList.add(fruit)
        }

        return fruitList
    }

    fun requestFruitData(timeService: TimeService, successCallback: (JSONArray, Int) -> Unit, failureCallback: (IOException) -> Unit) {
        val request = Request.Builder()
            .url("https://raw.githubusercontent.com/fmtvp/recruit-test-data/master/data.json")
            .addHeader("accept", "application/json")
            .build()
        val startTime = timeService.getTimeNow()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                failureCallback(e)
            }

            override fun onResponse(call: Call, response: Response) {
                val endTime = timeService.getTimeNow()
                val timePassed = timeService.getTimePassedInMillis(startTime, endTime)
                val responseAsObject = JSONObject(response.body()?.string())
                val responseBody = responseAsObject.getJSONArray("fruit")
                successCallback(responseBody, timePassed)
            }

        })
    }

}