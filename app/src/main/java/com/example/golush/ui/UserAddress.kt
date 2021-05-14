package com.example.golush.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.golush.AppData
import com.example.golush.AppFunctions
import com.example.golush.R
import com.example.golush.api.APIRequest
import com.example.golush.databinding.FragmentUserAddressBinding
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.fragment_user_address.*
import org.json.JSONObject

/**
 * A simple [Fragment] subclass.
 */
class UserAddress : Fragment() {

    lateinit var testView: View
    lateinit var binding: FragmentUserAddressBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserAddressBinding.inflate(layoutInflater, container, false)

        val label = HashMap<String, TextInputLayout>().apply {
            put("street_address", binding.labelStreetAddress)
            put("floor", binding.labelFloor)
            put("city", binding.labelCity)
            put("zip", binding.labelZipCode)
            put("state", binding.labelSate)
        }
        val input = LinkedHashMap<String, EditText>().apply {
            put("street_address", binding.inputStreetAddress)
            put("floor", binding.inputFloor)
            put("city", binding.inputCity)
            put("zip", binding.inputZipCode)
            put("state", binding.inputState)
        }

        for (key in input.keys) {
            input.get(key)!!.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable) {
                }

                override fun beforeTextChanged(
                    s: CharSequence, start: Int,
                    count: Int, after: Int
                ) {

                }

                override fun onTextChanged(
                    s: CharSequence, start: Int,
                    before: Int, count: Int
                ) {
                    AppFunctions.isEmpty(input.get(key)!!, label.get(key)!!)
                }
            })
        }

        binding.nextBtn.setOnClickListener {
            testView = it
            if (!AppFunctions.isEmpty(input, label)) {
                next_btn.apply {
                    isClickable = false
                    text = AppFunctions.getSpannableString(requireContext(), "Loading ", this)
                    AppFunctions.startButtonAnimation()
                }
                APIRequest(this, AppData.REGISTER_USER_DETAILS).execute(
                    AppFunctions.getData(input).apply {
                        for (key in AppData.formData.keys) {
                            put(key, AppData.formData.get(key)!!)
                        }
                    })
            }
        }

        return binding.root
    }


    fun onServerResponse(response: JSONObject) {
        next_btn.apply {
            text = "NEXT"
            isClickable = true
        }
        if (response.getString("status").equals("success")) {
            Navigation.findNavController(testView).navigate(R.id.action_userAddress_to_paymnet)
        } else {
//            binding.errorMessage.visibility = View.VISIBLE
        }
    }

}
