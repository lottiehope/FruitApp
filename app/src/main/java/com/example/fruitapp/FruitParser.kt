package com.example.fruitapp

import okhttp3.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException

class FruitParser {

    private val client = OkHttpClient()

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

    fun requestFruitData(successCallback: (JSONArray) -> Unit, failureCallback: () -> Unit) {
        val request = Request.Builder()
            .url("https://raw.githubusercontent.com/fmtvp/recruit-test-data/master/data.json")
            .addHeader("accept", "application/json")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                failureCallback()
            }

            override fun onResponse(call: Call, response: Response) {
                val objectJSON = JSONObject(response.body()?.string())
                val responseBody = objectJSON.getJSONArray("fruit")
                successCallback(responseBody)
            }

        })
    }

}