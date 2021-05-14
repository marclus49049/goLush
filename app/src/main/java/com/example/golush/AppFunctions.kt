package com.example.golush

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.text.SpannableString
import android.text.Spanned
import android.widget.Button
import android.widget.EditText
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.example.golush.ui.view.CustomDrawableSpan
import com.google.android.material.textfield.TextInputLayout

class AppFunctions {
    companion object {

        fun getData(input: LinkedHashMap<String, EditText>): HashMap<String, String> {
            return HashMap<String, String>().apply {
                for (key in input.keys) {
                    put(key, input.get(key)!!.text.toString())
                }
            }
        }

        // Checking if the inputs are empty or not
        fun isEmpty(
            input: LinkedHashMap<String, EditText>,
            lable: HashMap<String, TextInputLayout>
        ): Boolean {
            for (key in input.keys) {
                if (input.get(key)!!.text.isEmpty()) {
                    lable.get(key)!!.error = AppData.erroMessage.get("input_empty")
                    return true
                    break
                }
            }
            return false
        }

        // Checking if one input is empty or not
        fun isEmpty(input: EditText, label: TextInputLayout): Boolean {
            // if text is empty and clearInputsFlag is not set i.e. false
            if (!input.text.isEmpty()) {
                label.error = null
                return false
            }
            return true
        }

        // Clears all the inputs provided
        fun clearInputs(
            input: LinkedHashMap<String, EditText>,
            lable: HashMap<String, TextInputLayout>
        ) {
            for (key in input.keys) {
                lable.get(key)!!.error = null
                input.get(key)!!.text = null
            }
        }

        /**
         *
         * ProgressDialog
         *
         */
        var progressDrawable: CircularProgressDrawable? = null
        fun getSpannableString(context: Context, message: String, button: Button): SpannableString {
            progressDrawable = CircularProgressDrawable(context).apply {
                setStyle(CircularProgressDrawable.DEFAULT)
                setColorSchemeColors(Color.WHITE)

                val size = (centerRadius + strokeWidth).toInt() * 2
                setBounds(0, 0, size, size)
            }
            val drawableSpan = CustomDrawableSpan(progressDrawable!!, marginStart = 20)
            val spannableString = SpannableString(message).apply {
                setSpan(drawableSpan, length - 1, length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            }
            setCallBack(button)
            return spannableString
        }

        var callback: Drawable.Callback? = null
        fun setCallBack(button: Button) {
            callback = object : Drawable.Callback {
                override fun unscheduleDrawable(who: Drawable, what: Runnable) {
                }

                override fun invalidateDrawable(who: Drawable) {
                    button.invalidate()
                }

                override fun scheduleDrawable(who: Drawable, what: Runnable, `when`: Long) {
                }
            }
        }

        fun startButtonAnimation() {
            progressDrawable!!.apply {
                start()
                callback = AppFunctions.callback
            }
        }


        /**
         *
         *
         * Validations
         *
         */

        fun validateUsername(
            input: HashMap<String, EditText>,
            label: HashMap<String, TextInputLayout>
        ): Boolean {
            //validate username
            input.get("username")?.apply {
                if (!text.toString().matches(AppData.usernameRegex)) {
                    label.get("username")?.error = AppData.erroMessage.get("username_error")
                } else {
                    label.get("username")?.error = null
                }
            }
            return true
        }

        fun validatePassword(
            input: HashMap<String, EditText>,
            label: HashMap<String, TextInputLayout>
        ): Boolean {
            //Validate Password
            val password = input.get("password")?.text.toString()
            val confirm_password = input.get("confirm_password")?.text.toString()
            if (!password.matches(AppData.passwordRegex)) {
                label.get("password")?.error = AppData.erroMessage.get("password_error")
                return false
            } else if (!confirm_password.isEmpty() && !password.equals(confirm_password)) {
                label.get("password")?.error = AppData.erroMessage.get("password_match_error")
                label.get("confirm_password")?.error = " "
                return false
            } else {
                label.get("password")?.error = null
                label.get("confirm_password")?.error = null
            }
            return true
        }

        fun validateEmail(
            input: HashMap<String, EditText>,
            label: HashMap<String, TextInputLayout>
        ): Boolean {
            // Validate Email
            input.get("email")?.apply {
                if (!text.toString().isEmpty() && !text.toString().matches(AppData.emailRegex)) {
                    label.get("email")?.error = AppData.erroMessage.get("email_error")
                    return false
                } else {
                    label.get("email")?.error = null
                }
            }
            return true
        }

        fun validateContact(
            input: HashMap<String, EditText>,
            label: HashMap<String, TextInputLayout>
        ): Boolean {
            // Validate Contact
            input.get("contact")?.apply {
                if (!text.toString().isEmpty() && length() < 10) {
                    label.get("contact")?.error = AppData.erroMessage.get("contact_error")
                    return false
                } else {
                    label.get("contact")?.error = null
                }
            }
            return true
        }


        /**
         *
         *
         * Shared Pref
         *
         */

        fun sharedPref(context: Context, PREF_NAME: String): SharedPreferences {
            return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        }


    } // End of Object
}