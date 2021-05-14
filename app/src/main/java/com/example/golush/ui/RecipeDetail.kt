package com.example.golush.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.golush.AppData
import com.example.golush.R
import com.example.golush.adapter.IngredientsAdapter
import com.example.golush.api.APIRequest
import com.example.golush.databinding.FragmentRecipeDetailBinding
import com.example.golush.model.Ingredients
import org.json.JSONObject

/**
 * A simple [Fragment] subclass.
 */
class RecipeDetail : Fragment() {
    lateinit var testView: View
    lateinit var binding: FragmentRecipeDetailBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRecipeDetailBinding.inflate(layoutInflater, container, false)
        binding.shimmer.startShimmer()
        APIRequest(this, AppData.GET_ONE).execute()

        binding.startCookingBtn.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_recipeDetail_to_steps)
        }

        return binding.root
    }

    fun onServerResponse(response: JSONObject) {
        binding.shimmer.apply {
            stopShimmer()
            visibility = View.GONE
        }
        binding.startCookingBtn.isEnabled = true
        val jsonObject = JSONObject(response.getString("response"))
        try {
            Glide.with(this).load(jsonObject.getJSONArray("images").get(0).toString())
                .apply(RequestOptions.centerCropTransform()).into(binding.backgroundImage)
        } catch (ex: Exception) {

        }

        binding.recipeName.text = jsonObject.getString("name")
        binding.time.text = jsonObject.getString("time")
        setRecyclerView(
            ArrayList<Ingredients>().apply {
                for (index in 0 until jsonObject.getJSONArray("ingredients").length()) {
                    val json = jsonObject.getJSONArray("ingredients").getJSONObject(index)
                    add(
                        Ingredients(
                            json.getString("name"),
                            json.getString("amount"),
                            jsonObject.getString("serving")
                        )
                    )
                }
            }
        )
        AppData.steps = ArrayList<String>()
        for (index in 0 until jsonObject.getJSONArray("steps").length()) {
            AppData.steps.add(
                jsonObject.getJSONArray("steps").getString(index)
            )
        }
    }

    fun setRecyclerView(data: ArrayList<Ingredients>) {
        var adapter = IngredientsAdapter(this)
        binding.ingredientsRecycle.apply {
            this.layoutManager =
                LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
            this.itemAnimator = DefaultItemAnimator()
            this.adapter = adapter
        }
        adapter.setData(data)
        adapter.notifyDataSetChanged()
    }

}
