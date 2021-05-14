package com.example.golush

import com.example.golush.model.RecipesHome

class AppData {
    companion object {
        /**
         *
         * API Related
         *
         */
        var USER_TOKEN: String? = null
        var PLAN_TYPE: String? = null
        var RECEPIES: String? = null

        //        const val API_URL = "ec2-13-234-225-156.ap-south-1.compute.amazonaws.com:8080"
//        const val API_URL = "13.233.21.241:8080"  // API URL
        const val API_URL = "192.168.0.116:8080"  // API URL
        var USER_NAME: String? = null
        var USER_CONTACT: String? = null

        // Server Request Codes
        const val REGISTER_USER = 100
        const val REGISTER_USER_DETAILS = 101
        const val LOGIN = 102
        const val GET_ALL = 103
        const val GET_ONE = 104

        /**
         *
         * Error Messages
         *
         */
        val erroMessage = HashMap<String, String>().apply {
            put("login_error", "Your username and/or password do not match")
            put("input_empty", "You missed this")
            put("invalid_email", "That doesnt seem right")
            put("email_error", "Seems like that not a valid email")
            put("password_length_error", "Password must be more than 8 characters")
            put(
                "password_error",
                "Password must contain 8 characters, at least 1 uppercase letter, 1 lowercase letter, and 1 number"
            )
            put("password_match_error", "Passwords dont match")
            put("contact_error", "Mobile number is wrong")
            put("username_error", "Invalid Username")
        }

        val formData = HashMap<String, String>()

        /**
         *
         * Regex
         *
         */
        val emailRegex =
            "[a-z0-9!#\$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#\$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?".toRegex()
        val passwordRegex = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}\$".toRegex()
        val usernameRegex = "^(?!.*\\.\\.)(?!.*\\.\$)[^\\W][\\w.]{0,29}\$".toRegex()


        var recepie_id: Int = 0
        var fav_recepies = ArrayList<RecipesHome>()

        var steps = ArrayList<String>()

        var isFromFav: Boolean = false
    }
}