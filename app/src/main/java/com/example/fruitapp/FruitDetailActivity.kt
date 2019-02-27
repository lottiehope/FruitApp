package com.example.fruitapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_fruit_detail.*

class FruitDetailActivity : AppCompatActivity() {

    companion object {
        const val TYPE = "fruit name"
        const val PRICE = "price"
        const val WEIGHT = "weight"
    }

    private val statsTimeService = StatsTimeService()
    private lateinit var statsSender: StatsSender

    private val layoutChangeListener: View.OnLayoutChangeListener =
        View.OnLayoutChangeListener { _, _, _, _, _, _, _, _, _ ->
            statsTimeService.stopTimer()
            statsSender.createAndSendStat(
                StatsSender.StatsSenderRequestTypes.DISPLAY,
                statsTimeService.getDurationFromTimer().toString())
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        statsSender = StatsSender((this.applicationContext as FruitApplication).okHttpClient)
        statsTimeService.startTimer()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fruit_detail)

        val type = intent.getStringExtra(TYPE)
        val price = intent.getIntExtra(PRICE, 0)
        val weight = intent.getIntExtra(WEIGHT, 0)

        fruit_type.text = type
        fruit_price.text = getValueAsPoundsAndPence(price)
        fruit_weight.text = getValueAsKilograms(weight)

        fruit_detail_layout.addOnLayoutChangeListener(layoutChangeListener)
    }

    override fun onDestroy() {
        super.onDestroy()
        fruit_detail_layout.removeOnLayoutChangeListener(layoutChangeListener)
    }

    private fun getValueAsPoundsAndPence(value: Int): String {
        val priceInPounds = value/100.0
        return "£$priceInPounds"
    }

    private fun getValueAsKilograms(value: Int): String {
        return "${value/1000.0}kg"
    }
}
