package com.example.fruitapp

import com.nhaarman.mockitokotlin2.*
import junit.framework.Assert.assertEquals
import okhttp3.OkHttpClient
import okhttp3.Request
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class StatsSenderTest {

    @Mock private lateinit var mockOkHttpClient: OkHttpClient
    private lateinit var statsSender: StatsSender

    private val captor = argumentCaptor<Request>()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        statsSender = StatsSender(mockOkHttpClient)
        whenever(mockOkHttpClient.newCall(any())).thenReturn(mock())

    }

    @Test
    fun `When creating a stat the correct url is formed if request is error`() {
        statsSender.createAndSendStat(StatsSender.StatsSenderRequestTypes.ERROR, "Null pointer exception")
        verify(mockOkHttpClient).newCall(captor.capture())
        val actualRequest = captor.firstValue.url().toString()
        val expectedRequest = "https://raw2.github.com/fmtvp/recruit-test-data/master/stats?event=error&data=Null%20pointer%20exception"
        assertEquals(expectedRequest, actualRequest)

    }

    @Test
    fun `When creating a stat the correct url is formed if request is display`() {
        statsSender.createAndSendStat(StatsSender.StatsSenderRequestTypes.DISPLAY, "100")
        verify(mockOkHttpClient).newCall(captor.capture())
        val actualRequest = captor.firstValue.url().toString()
        val expectedRequest = "https://raw2.github.com/fmtvp/recruit-test-data/master/stats?event=display&data=100"
        assertEquals(expectedRequest, actualRequest)

    }

    @Test
    fun `When creating a stat the correct url is formed if request is load`() {
        statsSender.createAndSendStat(StatsSender.StatsSenderRequestTypes.LOAD, "50")
        verify(mockOkHttpClient).newCall(captor.capture())
        val actualRequest = captor.firstValue.url().toString()
        val expectedRequest = "https://raw2.github.com/fmtvp/recruit-test-data/master/stats?event=load&data=50"
        assertEquals(expectedRequest, actualRequest)
    }
}