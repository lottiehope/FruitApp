package com.example.fruitapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_fruit_detail.*
import java.math.RoundingMode
import java.text.DecimalFormat

class FruitDetailActivity : AppCompatActivity() {

    companion object {
        const val TYPE = "fruit name"
        const val PRICE = "price"
        const val WEIGHT = "weight"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fruit_detail)

        val type = intent.getStringExtra(TYPE)
        val price = intent.getIntExtra(PRICE, 0)
        val weight = intent.getIntExtra(WEIGHT, 0)

        fruit_type.text = type
        fruit_price.text = getValueAsPoundsAndPence(price)
        fruit_weight.text = getValueAsKilograms(weight)
    }

    private fun getValueAsPoundsAndPence(value: Int): String {
        return "£${value/100}"
    }

    private fun getValueAsKilograms(value: Int): String {
        return "${value/1000}kg"
    }
}
