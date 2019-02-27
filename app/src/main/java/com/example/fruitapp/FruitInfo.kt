package com.example.fruitapp

data class FruitCollection(val fruit: List<FruitInfo>)

data class FruitInfo(val type: String, val price: Int, val weight: Int)