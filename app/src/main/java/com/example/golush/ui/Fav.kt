package com.example.golush.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.golush.AppData
import com.example.golush.R
import com.example.golush.adapter.RecipesHomeAdapter
import com.example.golush.databinding.FragmentFavBinding
import com.example.golush.model.RecipesHome

/**
 * A simple [Fragment] subclass.
 */
class Fav : Fragment() {

    lateinit var testView: View
    lateinit var binding: FragmentFavBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentFavBinding.inflate(layoutInflater, container, false)

        (requireContext() as MainActivity).binding.bottomNavigation.visibility = View.VISIBLE
        if (!AppData.fav_recepies.isEmpty()) {
            setRecyclerView(AppData.fav_recepies)
            binding.paymentBtn.apply {
                AppData.isFromFav = true
                isEnabled = true
                setOnClickListener {
                    Navigation.findNavController(it).navigate(R.id.action_fav_to_paymentSuccess)
                    AppData.fav_recepies = ArrayList<RecipesHome>()
                }
            }
        }
        return binding.root
    }

    fun setRecyclerView(data: ArrayList<RecipesHome>) {
        var adapter = RecipesHomeAdapter(this, 1)
        binding.recipesRecycle.apply {
            this.layoutManager =
                LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
            this.itemAnimator = DefaultItemAnimator()
            this.adapter = adapter
        }
        adapter.setData(data)
        adapter.notifyDataSetChanged()
    }


}
