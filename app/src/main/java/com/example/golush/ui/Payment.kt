package com.example.golush.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.golush.AppData
import com.example.golush.R
import com.example.golush.databinding.FragmentPaymnetBinding

/**
 * A simple [Fragment] subclass.
 */
class Payment : Fragment() {

    lateinit var binding: FragmentPaymnetBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentPaymnetBinding.inflate(layoutInflater, container, false)

        binding.paymentBtn.setOnClickListener {
            AppData.isFromFav = false
            Navigation.findNavController(it).navigate(R.id.action_payment_to_paymentSuccess)
        }

        return binding.root
    }

}
