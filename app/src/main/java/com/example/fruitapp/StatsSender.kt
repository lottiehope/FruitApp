package com.example.fruitapp

import android.util.Log
import okhttp3.*
import java.io.IOException

class StatsSender {

    private val client = OkHttpClient()

    enum class StatsSenderRequestTypes(val requestTypeString: String) {
        ERROR("error"),
        LOAD("load"),
        DISPLAY("display")
    }

    fun createAndSendStat(requestType: StatsSenderRequestTypes, data: String) {
        val request = Request.Builder()
            .url("https://raw2.github.com/fmtvp/recruit-test-data/master/stats?event=${requestType.requestTypeString}&data=$data")
            .build()
        sendStat(request)
    }

    private fun sendStat(request: Request) {
        Log.d("Sending this request now", request.toString())
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.d("Stat Request", "Failed to send")
            }

            override fun onResponse(call: Call, response: Response) {
                Log.d("Stat Request", "Successfully sent")
            }
        })
    }

}