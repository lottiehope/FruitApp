package com.example.fruitapp

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Test
import org.junit.Before
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class StatsTimeServiceTest {

    @Mock
    private lateinit var mockTimeService: TimeService

    private lateinit var statsTimeService: StatsTimeService

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        statsTimeService = StatsTimeService(mockTimeService)
        whenever(mockTimeService.getTimeNow()).thenReturn(100L)
    }

    @Test
    fun `starting the timer gets the current time`() {
        statsTimeService.startTimer()
        verify(mockTimeService).getTimeNow()
    }

    @Test
    fun `ending the timer gets the current time`() {
        statsTimeService.startTimer()
        statsTimeService.stopTimer()
        verify(mockTimeService).getTimeNow()
    }

    @Test
    fun getDurationFromTimer() {
        statsTimeService.startTimer()
        statsTimeService.stopTimer()
        whenever(mockTimeService.getTimePassedInMillis(any(), any())).thenReturn(100)
        statsTimeService.getDurationFromTimer()
        verify(mockTimeService).getTimePassedInMillis(any(), any())
    }
}