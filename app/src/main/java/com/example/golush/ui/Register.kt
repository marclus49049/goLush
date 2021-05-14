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
import com.example.golush.databinding.FragmentRegisterBinding
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.fragment_register.*
import org.json.JSONObject

/**
 * A simple [Fragment] subclass.
 */
class Register : Fragment() {

    lateinit var testView: View
    lateinit var binding: FragmentRegisterBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(layoutInflater, container, false)
        val label = HashMap<String, TextInputLayout>().apply {
            put("fullname", binding.labelName)
            put("username", binding.labelUsername)
            put("password", binding.labelPassword)
            put("confirm_password", binding.labelPasswordConfirm)
            put("email", binding.labelEmail)
            put("contact", binding.labelContact)
        }
        val input = LinkedHashMap<String, EditText>().apply {
            put("fullname", binding.inputName)
            put("username", binding.inputUsername)
            put("password", binding.inputPassword)
            put("confirm_password", binding.inputPasswordConfirm)
            put("email", binding.inputEmail)
            put("contact", binding.inputContact)
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
                        binding.errorMessage.visibility = View.INVISIBLE
                        AppFunctions.isEmpty(input.get(key)!!, label.get(key)!!)
                    }
                })
                setOnFocusChangeListener { view: View, hasFocus: Boolean ->
                    if (!hasFocus) {
                        validateInputs(input, label, key)
                    }
                }

            }

        }

        binding.registerBtn.setOnClickListener {
            testView = it
            if (!AppFunctions.isEmpty(input, label)) {
                if (validateInputs(input, label, "")) {
                    register_btn.apply {
                        isClickable = false
                        text = AppFunctions.getSpannableString(requireContext(), "Loading ", this)
                        AppFunctions.startButtonAnimation()
                        AppData.USER_NAME = input.get("username")!!.text.toString()
                        AppData.USER_CONTACT = input.get("contact")!!.text.toString()
                        APIRequest(
                            this@Register,
                            AppData.REGISTER_USER
                        ).execute(AppFunctions.getData(input))
                    }
                }
            }
        }

        return binding.root
    }

    fun onServerResponse(response: JSONObject) {
        register_btn.apply {
            text = "Register"
            isClickable = true
        }
        if (response.getString("status").equals("success")) {
            Navigation.findNavController(testView).navigate(R.id.action_register_to_select_plan)
        } else {
            binding.errorMessage.visibility = View.VISIBLE
            binding.labelUsername.error = "Username Already Exists"
            binding.labelContact.error = "Mobile Already Exists"
        }
    }


    fun validateInputs(
        input: HashMap<String, EditText>,
        label: HashMap<String, TextInputLayout>,
        key: String
    ): Boolean {
        when (key) {
            "username" -> {
                return AppFunctions.validateUsername(input, label)
            }
            "password" -> {
                return AppFunctions.validatePassword(input, label)
            }
            "email" -> {
                return AppFunctions.validateEmail(input, label)
            }
            "contact" -> {
                return AppFunctions.validateContact(input, label)
            }
            else -> {
                return AppFunctions.validateUsername(input, label)
                        && AppFunctions.validatePassword(input, label)
                        && AppFunctions.validateEmail(input, label)
                        && AppFunctions.validateContact(input, label)
            }
        }
    }
}
