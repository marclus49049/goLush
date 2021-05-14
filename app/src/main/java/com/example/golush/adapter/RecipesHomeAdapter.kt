package com.example.golush.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.golush.AppData
import com.example.golush.R
import com.example.golush.model.RecipesHome
import com.google.android.material.card.MaterialCardView
import com.google.android.material.dialog.MaterialAlertDialogBuilder

private const val TYPE_HOME = 0
private const val TYPE_SELECTED = 1

class RecipesHomeAdapter(val fragment: Fragment, val type: Int) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var list = ArrayList<RecipesHome>()
    override fun getItemViewType(position: Int): Int {
        return type
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var view: View
        view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_recipe_home, parent, false)
        return HomeViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    var index: Int? = null
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentItem = list[position]


        (holder as HomeViewHolder).apply {

            if (AppData.fav_recepies.contains(currentItem)) {
                select_btn.apply {
                    isSelected = true
                    text = "Selected"
                }
            }

            textName.text = currentItem.name
            textTime.text = currentItem.time
            try {
                Glide.with(fragment).load(currentItem.imageURL)
                    .apply(RequestOptions.centerCropTransform()).into(image)
            } catch (ex: Exception) {

            }


            card.setOnClickListener {
                Navigation.findNavController(it).navigate(R.id.action_home4_to_recipeDetail)
                AppData.recepie_id = currentItem.id
            }

            fav_btn.setOnClickListener {
                it as ImageButton
                it.isSelected = !it.isSelected
            }

            select_btn.setOnClickListener {
                it as Button
                if (it.isSelected) {
                    it.text = "Select"
                    AppData.fav_recepies.remove(currentItem)
                    it.isSelected = !it.isSelected
                } else if (!it.isSelected && AppData.fav_recepies.size == AppData.RECEPIES!!.toInt()) {
                    MaterialAlertDialogBuilder(fragment.requireContext())
                        .setMessage("Your plan only allows you to add ${AppData.RECEPIES} recipes")
                        .setPositiveButton("Ok", null)
                        .show()
                } else {
                    it.text = "Selected"
                    AppData.fav_recepies.add(currentItem)
                    it.isSelected = !it.isSelected
                }

            }

            if (type == TYPE_SELECTED) {
                select_btn.visibility = View.GONE
            }

        }
    }

    /**
     *
     * SET DATA
     *
     */

    fun setData(newList: ArrayList<RecipesHome>) {
        val oldList = list
        this.list = ArrayList<RecipesHome>()
        this.list.addAll(newList)
    }

    /**
     *
     * View Holders
     *
     */

    class HomeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textName = itemView.findViewById<TextView>(R.id.text_recipes_name)
        val textTime = itemView.findViewById<TextView>(R.id.text_time)
        val image = itemView.findViewById<ImageView>(R.id.image_view)
        val fav_btn = itemView.findViewById<ImageButton>(R.id.fav_btn)
        val card = itemView.findViewById<MaterialCardView>(R.id.card)
        val select_btn = itemView.findViewById<Button>(R.id.select_btn)
    }
}