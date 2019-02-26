package com.example.fruitapp

class TimeService {

    fun getTimeNow(): Long {
        return System.currentTimeMillis()
    }

    fun getTimePassedInMillis(startTime: Long, endTime: Long): Int {
        return (endTime - startTime).toInt()
    }

}