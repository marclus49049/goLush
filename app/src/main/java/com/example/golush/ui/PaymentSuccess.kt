package com.example.golush.ui

import android.graphics.drawable.Animatable
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.golush.AppData
import com.example.golush.R
import com.example.golush.databinding.FragmentPaymentSuccessBinding

/**
 * A simple [Fragment] subclass.
 */
class PaymentSuccess : Fragment() {

    lateinit var binding: FragmentPaymentSuccessBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentPaymentSuccessBinding.inflate(layoutInflater, container, false)

        Handler().postDelayed(
            {
                binding.progressBar.visibility = View.GONE
                binding.androidAnim.drawable.apply {
                    this as Animatable
                    start()
                }

                if (AppData.isFromFav) {
                    binding.text.text = "Order Placed !"
                    binding.text1.text = "Your order has been placed succesfully"
                } else {
                    binding.text.text = "Success !"
                    binding.text1.text = "Your payment was succesful"
                }

                binding.doneBtn.apply {
                    text = if (AppData.isFromFav) "Go Home" else "Go to Login"
                    setOnClickListener {
                        Navigation.findNavController(it)
                            .navigate(if (AppData.isFromFav) R.id.action_paymentSuccess_to_home4 else R.id.action_paymentSuccess_to_login)
                    }
                }
            },
            800
        )

        return binding.root
    }

}
