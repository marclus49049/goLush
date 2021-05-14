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
import com.example.golush.databinding.FragmentLoginBinding
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.fragment_login.*
import org.json.JSONObject


class Login : Fragment() {

    lateinit var testView: View
    lateinit var binding: FragmentLoginBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(layoutInflater, container, false)

        val label = HashMap<String, TextInputLayout>().apply {
            put("username", binding.labelUsername)
            put("password", binding.labelPassword)
        }
        val input = LinkedHashMap<String, EditText>().apply {
            put("username", binding.inputUsername)
            put("password", binding.inputPassword)
        }

        for (key in input.keys) {
            input.get(key)!!.apply {
                addTextChangedListener(object : TextWatcher {
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

                setOnFocusChangeListener { v, hasFocus ->
                    if (hasFocus) {
                        for (key in label.keys) {
                            label.get(key)!!.error = null
                        }
                        binding.errorMessage.visibility = View.INVISIBLE
                    }
                }
            }
        }

        binding.loginBtn.setOnClickListener {
            testView = it
            if (!AppFunctions.isEmpty(input, label)) {
                login_btn.apply {
                    isClickable = false
                    text = AppFunctions.getSpannableString(requireContext(), "Loading ", this)
                    AppFunctions.startButtonAnimation()
                }
                APIRequest(this, AppData.LOGIN).execute(AppFunctions.getData(input))
            }

        }

        return binding.root
    }


    fun onServerResponse(response: JSONObject) {
        login_btn.apply {
            text = "Login"
            isClickable = true
        }
        if (response.getString("status").equals("success")) {
            AppData.USER_TOKEN = response.getString("user_token")
            AppData.RECEPIES = response.getString("number_of_recipes")
            AppData.PLAN_TYPE = response.getString("plan_type")
            AppFunctions.sharedPref(requireContext(), "GoLush").apply {
                edit().apply {
                    putString("user_token", AppData.USER_TOKEN)
                    apply()
                }
            }
            Navigation.findNavController(testView).navigate(R.id.action_login_to_home)
        } else {
            binding.errorMessage.visibility = View.VISIBLE
            binding.labelUsername.error = " "
            binding.labelPassword.error = " "
        }
    }


}
