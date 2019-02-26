package com.example.fruitapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import okhttp3.OkHttpClient

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    private val client = OkHttpClient()
    private val parser = FruitParser(client)
    private val statsSender = StatsSender(client)
    private val timeService = TimeService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView = findViewById(R.id.main_fruit_list)
        getFruit()

        val refreshLayout = findViewById<SwipeRefreshLayout>(R.id.refresh_main)
        refreshLayout.setOnRefreshListener {
            refreshLayout.isRefreshing = true
            recyclerView.removeAllViewsInLayout()
            getFruit()
            refreshLayout.isRefreshing = false
        }

    }

    private fun getFruit() {
        parser.requestFruitData(
            timeService,
            successCallback = { data, timePassed ->
                statsSender.createAndSendStat(StatsSender.StatsSenderRequestTypes.LOAD, timePassed.toString())
                runOnUiThread{
                    viewAdapter = FruitListAdapter(parser.parseFruitDataIntoFruitInfo(data))
                    recyclerView.apply {
                        layoutManager = viewManager
                        adapter = viewAdapter
                    }
                }
            },
            failureCallback = {
                Log.d("Network request failed: ", "Failed to get fruit data")
                statsSender.createAndSendStat(StatsSender.StatsSenderRequestTypes.ERROR, it.toString())
            }
        )
    }

}
