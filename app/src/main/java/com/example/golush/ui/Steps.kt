package com.example.golush.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.golush.AppData
import com.example.golush.adapter.StepsAdapter
import com.example.golush.databinding.FragmentStepsBinding

/**
 * A simple [Fragment] subclass.
 */
class Steps : Fragment() {
    lateinit var binding: FragmentStepsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStepsBinding.inflate(layoutInflater, container, false)

        binding.viewPager.apply {
            val viewPagerAdapter = StepsAdapter(this@Steps.requireFragmentManager())
            adapter = viewPagerAdapter
        }

        binding.nextBtn.apply {
            setOnClickListener {
                if (binding.viewPager.currentItem != AppData.steps.size - 2) {
                    text = "Next"
                } else {
                    text = "Done"
                }
                if (binding.viewPager.currentItem != AppData.steps.size - 1) {
                    binding.viewPager.currentItem = binding.viewPager.currentItem + 1
                } else {
                    fragmentManager?.popBackStack()
                }
            }
        }

        return binding.root
    }

}
