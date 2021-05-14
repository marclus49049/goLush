package com.example.golush.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.golush.R
import com.example.golush.model.Ingredients

private const val TYPE_HOME = 0

class IngredientsAdapter(val fragment: Fragment) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var list = ArrayList<Ingredients>()
    override fun getItemViewType(position: Int): Int {
        return TYPE_HOME
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var view: View
        view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_ingredients, parent, false)
        return HomeViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    var index: Int? = null
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentItem = list[position]
        print(currentItem)
        (holder as HomeViewHolder).apply {
            name.text = currentItem.name
            amount.text = currentItem.amount
            var matches = currentItem.serving.replace(Regex("[^0-9]"), "")
            serving.text = "For ${matches} persons"
        }
    }

    /**
     *
     * SET DATA
     *
     */

    fun setData(newList: ArrayList<Ingredients>) {
        this.list = ArrayList<Ingredients>()
        this.list.addAll(newList)
    }

    /**
     *
     * View Holders
     *
     */

    class HomeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name = itemView.findViewById<TextView>(R.id.name)
        val amount = itemView.findViewById<TextView>(R.id.amount)
        val serving = itemView.findViewById<TextView>(R.id.serving)

    }
}