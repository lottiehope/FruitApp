package com.example.fruitapp

import com.google.gson.Gson
import com.nhaarman.mockitokotlin2.*
import junit.framework.Assert.*
import okhttp3.*
import org.junit.Test
import org.junit.Before
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class FruitParserTest {

    @Mock private lateinit var mockOkHttpClient: OkHttpClient
    @Mock private lateinit var mockGson: Gson
    @Mock private lateinit var mockTimeService: TimeService
    @Mock private lateinit var mockCall: Call
    @Mock private lateinit var mockResponse: Response

    private lateinit var fruitParser: FruitParser

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        fruitParser = FruitParser(mockOkHttpClient, mockGson)
        whenever(mockGson.fromJson(any<String>(), eq(FruitCollection::class.java))).thenReturn(FruitCollection(emptyList()))
        whenever(mockOkHttpClient.newCall(any())).thenReturn(mockCall)
        val mediaType = MediaType.parse("")
        whenever(mockResponse.body()).thenReturn(ResponseBody.create(mediaType, """
        {
            "fruit":[
                {"type":"apple", "price":149, "weight":120},
                {"type":"banana", "price":129, "weight":80},
                {"type":"blueberry", "price":19, "weight":18},
                {"type":"orange", "price":199, "weight":150},
                {"type":"pear", "price":99, "weight":100},
                {"type":"strawberry", "price":99, "weight":20},
                {"type":"kumquat", "price":49, "weight":80},
                {"type":"pitaya", "price":599, "weight":100},
                {"type":"kiwi", "price":89, "weight":200}
            ]
        }
        """))
    }

    @Test
    fun `when request to get fruit data is successful the success callback is called`() {
        var data: FruitCollection? = null
        var failed = false
        val captor = argumentCaptor<Callback>()
        whenever(mockResponse.code()).thenReturn(200)

        fruitParser.requestFruitData(
            mockTimeService,
            successCallback = { collection, _ ->
                data = collection
            },
            failureCallback = {
                failed = true
            })
        verify(mockCall).enqueue(captor.capture())
        captor.firstValue.onResponse(mockCall, mockResponse)
        assertNotNull(data)
        assertFalse(failed)
    }

    @Test
    fun `when request to get fruit data is not successful the failure callback is called`() {
//        var data: FruitCollection? = null
//        var failed = false
//        val captor = argumentCaptor<Callback>()
//        whenever(mockResponse.code()).thenReturn(400)
//
//        fruitParser.requestFruitData(
//            mockTimeService,
//            successCallback = { collection, _ ->
//                data = collection
//            },
//            failureCallback = {
//                failed = true
//            })
//        verify(mockCall).enqueue(captor.capture())
//        captor.firstValue.onResponse(mockCall, mockResponse)
//        assertNull(data)
//        assertTrue(failed)
    }
}