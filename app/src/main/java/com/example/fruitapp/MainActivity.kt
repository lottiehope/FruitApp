package com.example.fruitapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView

    private lateinit var viewAdapter: RecyclerView.Adapter<*>

    private lateinit var viewManager: RecyclerView.LayoutManager

    private val parser = FruitParser()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView = findViewById(R.id.main_fruit_list)
        getFruit()

    }

    private fun getFruit() {
        parser.requestFruitData(
            successCallback = { data ->
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
                getFruit()
            }
        )
    }

}
