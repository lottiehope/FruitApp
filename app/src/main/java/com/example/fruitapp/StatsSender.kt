package com.example.fruitapp

import okhttp3.*
import java.io.IOException

class StatsSender(private val client: OkHttpClient) {

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
        val call = client.newCall(request)
        call.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                //TODO: what should happen on failure? Repeat a few times or just give up
            }

            override fun onResponse(call: Call, response: Response) {
                //Do nothing
            }
        })
    }

}