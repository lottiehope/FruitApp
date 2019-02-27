package com.example.fruitapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import com.google.gson.Gson
import org.json.JSONArray
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.OkHttpClient

class MainActivity : AppCompatActivity() {

    private lateinit var okHttpClient: OkHttpClient
    private lateinit var gson: Gson

    private lateinit var fruitParser: FruitParser
    private lateinit var statsSender: StatsSender

    private val timeService = TimeService()
    private val statsTimeService = StatsTimeService()

    private val layoutChangeListener: View.OnLayoutChangeListener =
        View.OnLayoutChangeListener { _, _, _, _, _, _, _, _, _ ->
            statsTimeService.stopTimer()
            statsSender.createAndSendStat(
                StatsSender.StatsSenderRequestTypes.DISPLAY,
                statsTimeService.getDurationFromTimer().toString())
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        okHttpClient = (this.applicationContext as FruitApplication).okHttpClient
        gson = (this.applicationContext as FruitApplication).gson
        statsSender = StatsSender(okHttpClient)
        fruitParser = FruitParser(okHttpClient, gson)

        getFruit()

        refresh_main.setOnRefreshListener {
            refresh_main.isRefreshing = true
            main_fruit_list.removeAllViewsInLayout()
            getFruit()
            refresh_main.isRefreshing = false
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        main_fruit_list.removeOnLayoutChangeListener(layoutChangeListener)
    }


    private fun addDataToRecyclerView(data: FruitCollection) {
        runOnUiThread{
            statsTimeService.startTimer()
            val viewAdapter = FruitListAdapter(data.fruit)
            val viewManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

            main_fruit_list.apply {
                this.addOnLayoutChangeListener(layoutChangeListener)
                layoutManager = viewManager
                adapter = viewAdapter
            }
        }
    }

    private fun getFruit() {
        fruitParser.requestFruitData(
            timeService,
            successCallback = { data, timePassed ->
                statsSender.createAndSendStat(StatsSender.StatsSenderRequestTypes.LOAD, timePassed.toString())
                addDataToRecyclerView(data)
            },
            failureCallback = {
                Log.d("Network request failed: ", "Failed to get fruit data")
                statsSender.createAndSendStat(StatsSender.StatsSenderRequestTypes.ERROR, it.toString())
            }
        )
    }

}
