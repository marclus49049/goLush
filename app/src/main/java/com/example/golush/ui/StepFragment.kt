package com.example.golush.ui


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.golush.AppData
import com.example.golush.databinding.FragmentStepBinding


/**
 * A simple [Fragment] subclass.
 */
class StepFragment(val index: Int) : Fragment() {
    lateinit var binding: FragmentStepBinding

    // Inflate the view for the fragment based on layout XML
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStepBinding.inflate(layoutInflater, container, false)
        binding.stepNum.text = "Step ${index + 1}"
        binding.step.text = "${AppData.steps.get(index)}"
        return binding.root
    }
}
