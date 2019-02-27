package com.example.fruitapp

import org.junit.Test
import org.junit.Assert.*
import org.junit.Before
import org.mockito.MockitoAnnotations

class TimeServiceTest {


    private val timeService = TimeService()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun `getTimeNow returns the time now`() {
    }

    @Test
    fun `getTimePassedInMillis will get the correct time between start time and end time`() {
        val time = timeService.getTimePassedInMillis(100, 1000)
        assertEquals(900, time)
    }
}