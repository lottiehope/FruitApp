package com.example.fruitapp

import android.content.Intent
import android.support.constraint.ConstraintLayout
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fruit_item_cell.view.*

class FruitListAdapter(private val fruitList: List<FruitInfo>) : RecyclerView.Adapter<FruitListAdapter.FruitListViewHolder>() {

    class FruitListViewHolder(val layoutView: ConstraintLayout) : RecyclerView.ViewHolder(layoutView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FruitListAdapter.FruitListViewHolder {
        val textView = LayoutInflater.from(parent.context).inflate(R.layout.fruit_item_cell, parent, false) as ConstraintLayout
        return FruitListViewHolder(textView)
    }

    override fun onBindViewHolder(holder: FruitListViewHolder, position: Int) {
        val fruit = fruitList[position]
        holder.layoutView.fruit_cell_title.text = fruit.type.capitalize()
        holder.layoutView.fruit_cell.setOnClickListener {
            val fruitDetailIntent = createIntentFromFruitInfo(it, fruit)
            startActivity(it.context, fruitDetailIntent, null)
        }
    }

    private fun createIntentFromFruitInfo(view: View, fruit: FruitInfo): Intent {
        val fruitDetailIntent = Intent(view.context, FruitDetailActivity::class.java)
        fruitDetailIntent.putExtra(FruitDetailActivity.TYPE, fruit.type.capitalize())
        fruitDetailIntent.putExtra(FruitDetailActivity.PRICE, fruit.price)
        fruitDetailIntent.putExtra(FruitDetailActivity.WEIGHT, fruit.weight)
        return fruitDetailIntent
    }

    override fun getItemCount(): Int {
        return fruitList.size
    }

}