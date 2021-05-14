package com.example.golush.ui


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.golush.AppData
import com.example.golush.adapter.RecipesHomeAdapter
import com.example.golush.api.APIRequest
import com.example.golush.databinding.FragmentHomeBinding
import com.example.golush.model.RecipesHome
import org.json.JSONObject

/**
 * A simple [Fragment] subclass.
 */
class Home : Fragment() {
    lateinit var testView: View
    lateinit var binding: FragmentHomeBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        AppData.fav_recepies = ArrayList<RecipesHome>()

        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        (requireContext() as MainActivity).binding.bottomNavigation.visibility = View.VISIBLE
        binding.shimmer.startShimmer()

        APIRequest(this, AppData.GET_ALL).execute()

        return binding.root
    }

    fun onServerResponse(response: JSONObject) {
        binding.shimmer.apply {
            stopShimmer()
            visibility = View.GONE
        }
        val jsonObject = response.getJSONObject("response")
        setRecyclerView(
            ArrayList<RecipesHome>().apply {
                for (key in jsonObject.keys()) {
                    val json = JSONObject(jsonObject.getString(key))
                    add(
                        RecipesHome(
                            key.toInt(),
                            json.getString("name"),
                            json.getString("serving"),
                            json.getString("time"),
                            json.getString("type"),
                            json.getJSONArray("images").get(0).toString()
                        )
                    )
                }
            }
        )
    }

    fun setRecyclerView(data: ArrayList<RecipesHome>) {
        var adapter = RecipesHomeAdapter(this, 0)
        binding.recipesRecycle.apply {
            this.layoutManager =
                LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
            this.itemAnimator = DefaultItemAnimator()
            this.adapter = adapter
            setItemViewCacheSize(50)
            recycledViewPool.setMaxRecycledViews(0, 50)
        }
        adapter.setData(data)
        adapter.notifyDataSetChanged()
    }


}
