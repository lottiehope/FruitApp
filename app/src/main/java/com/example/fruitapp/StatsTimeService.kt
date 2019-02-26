package com.example.fruitapp

import java.lang.Exception

class StatsTimeService {

    private val timeService = TimeService()

    private var timeAtStart = -1L
    private var timeAtEnd = -1L

    fun startTimer() {
        timeAtStart = timeService.getTimeNow()
    }

    fun stopTimer() {
        if(timeAtStart == -1L) {
            throw Exception("Cannot stop timer before it has been started")
        }

        timeAtEnd = timeService.getTimeNow()
    }

    fun getDurationFromTimer(): Int {
        if(timeAtStart == -1L || timeAtEnd == -1L) {
            throw Exception("Timer has either not been started or ended")
        }

        val timePassed = timeService.getTimePassedInMillis(timeAtStart, timeAtEnd)
        resetTimer()
        return timePassed
    }

    private fun resetTimer() {
        timeAtStart = -1L
        timeAtEnd = -1L
    }

}