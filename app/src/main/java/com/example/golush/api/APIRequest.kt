package com.example.golush.api

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.AsyncTask
import android.os.Handler
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.golush.AppData
import com.example.golush.ui.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.util.concurrent.TimeUnit


private const val URL: String = AppData.API_URL

const val DELAY: Long = 2000

class APIRequest(val fragment: Fragment, val type: Int) :
    AsyncTask<HashMap<String, String>, Void, String>() {

    var serverResponse: String? = null

    val client = OkHttpClient.Builder().readTimeout(2000, TimeUnit.MILLISECONDS)
        .connectTimeout(2000, TimeUnit.MILLISECONDS)
        .writeTimeout(2000, TimeUnit.MILLISECONDS)
        .build()

    var startTime: Long? = null
    var endTime: Long? = null

    override fun doInBackground(vararg params: HashMap<String, String>): String {
        return try {
            startTime = System.currentTimeMillis()
            when (type) {
                AppData.REGISTER_USER -> {
                    return registerUser(params.get(0)).toString()
                }
                AppData.LOGIN -> {
                    return loginUser(params.get(0)).toString()
                }
                AppData.REGISTER_USER_DETAILS -> {
                    return registerUserDetails(params.get(0)).toString()
                }
                AppData.GET_ALL -> {
                    return getAllRecepies().toString()
                }
                AppData.GET_ONE -> {
                    return getRecepie().toString()
                }
                else -> {
                    return ""
                }
            }

        } catch (e: Exception) {
            val connectivityManager =
                fragment.requireContext()
                    .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = connectivityManager.activeNetworkInfo
            if (networkInfo != null && networkInfo.isConnected) {
            } else {
                fragment.requireActivity().runOnUiThread({
                    Toast.makeText(fragment.requireContext(), "Test", Toast.LENGTH_LONG).show()
                })
            }
            return ""
        }
    }

    override fun onPostExecute(result: String?) {
        super.onPostExecute(result)
        try {
//            println(serverResponse)
            var serverResponseJson: JSONObject? = null
            endTime = System.currentTimeMillis()
            val time = endTime!! - startTime!!
            if (serverResponse != null) {
                serverResponseJson = JSONObject(serverResponse!!)
//                Log.d("Server", serverResponseJson.toString())
                if (time < DELAY) {
                    Handler().postDelayed(
                        Runnable {
                            sendToContext(serverResponseJson)
                        },
                        (DELAY - time)
                    )
                } else {
                    fragment.requireActivity()
                        .startActivity(Intent(fragment.requireContext(), NetworkError::class.java))
                    fragment.requireActivity().finish()
//                    sendToContext(serverResponseJson!!)
                }
            } else {
                fragment.requireActivity()
                    .startActivity(Intent(fragment.requireContext(), NetworkError::class.java))
                fragment.requireActivity().finish()
            }
        } catch (e: java.lang.Exception) {
            // Server Error Fragment
            fragment.requireActivity()
                .startActivity(Intent(fragment.requireContext(), NetworkError::class.java))
            fragment.requireActivity().finish()
        }
    }

    fun sendToContext(response: JSONObject) {
        when (fragment) {
            is Register -> {
                fragment.onServerResponse(response)
            }
            is Login -> {
                fragment.onServerResponse(response)
            }
            is UserAddress -> {
                fragment.onServerResponse(response)
            }
            is Home -> {
                fragment.onServerResponse(response)
            }
            is RecipeDetail -> {
                fragment.onServerResponse(response)
            }
        }
    }


    fun registerUser(formData: HashMap<String, String>) {
        val jsonObject = JSONObject()
        for (key in formData.keys) {
            jsonObject.put(key, formData.get(key))
        }
        val request = Request.Builder().apply {
            url("http://${URL}/register")
            post(jsonObject.toString().toRequestBody(MEDIA_TYPE_JSON))
        }.build()

        val response = client.newCall(request).execute()
        serverResponse = response.body!!.string()
        serverResponse
    }

    fun registerUserDetails(formData: HashMap<String, String>) {
        val jsonObject = JSONObject()
        for (key in formData.keys) {
            jsonObject.put(key, formData.get(key))
        }
        jsonObject.put("username", AppData.USER_NAME)
        jsonObject.put("contact", AppData.USER_CONTACT)

        val request = Request.Builder().apply {
            url("http://${URL}/user_details")
            post(jsonObject.toString().toRequestBody(MEDIA_TYPE_JSON))
        }.build()

        val response = client.newCall(request).execute()
        serverResponse = response.body!!.string()
        serverResponse
    }

    fun loginUser(formData: HashMap<String, String>) {
        val jsonObject = JSONObject()
        for (key in formData.keys) {
            jsonObject.put(key, formData.get(key))
        }
        val request = Request.Builder().apply {
            url("http://${URL}/login")
            post(jsonObject.toString().toRequestBody(MEDIA_TYPE_JSON))
        }.build()

        val response = client.newCall(request).execute()
        serverResponse = response.body!!.string()
        serverResponse
    }

    fun getAllRecepies() {
        val request = Request.Builder().apply {
            AppData.USER_TOKEN?.let { header("Authorization", it) }
            url("http://${URL}/get_all_recepie")
            get()
        }.build()

        val response = client.newCall(request).execute()
        serverResponse = response.body!!.string()
        serverResponse
    }

    fun getRecepie() {
        val request = Request.Builder().apply {
            AppData.USER_TOKEN?.let { header("Authorization", it) }
            url("http://${URL}/get_recepie/${AppData.recepie_id}")
            get()
        }.build()

        val response = client.newCall(request).execute()
        serverResponse = response.body!!.string()
        serverResponse
    }

    companion object {
        val MEDIA_TYPE_JSON = "application/json; charset=utf-8".toMediaType()
    }

}
