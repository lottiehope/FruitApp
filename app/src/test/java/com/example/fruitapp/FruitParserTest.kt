package com.example.fruitapp

import okhttp3.OkHttpClient
import org.junit.Test

import org.junit.Assert.*

class FruitParserTest {

    private val client = OkHttpClient()

    private val fruitParser = FruitParser(client)

    private val sampleData = 1

    @Test
    fun `JSON gets converted into a list of fruit info objects`() {
//        fruitParser.parseFruitDataIntoFruitInfo()
    }

    @Test
    fun `when request to get fruit data is successful the success callback is called`() {
//        fruitParser.requestFruitData()
    }

    @Test
    fun `when request to get fruit data is not successful the failure callback is called`() {
    }
}