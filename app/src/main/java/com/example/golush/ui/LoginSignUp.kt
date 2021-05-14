package com.example.golush.ui


import android.media.AudioManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.golush.R
import com.example.golush.databinding.FragmentLoginSignUpBinding

/**
 * A simple [Fragment] subclass.
 */
class LoginSignUp : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentLoginSignUpBinding.inflate(layoutInflater, container, false)

        binding.bgImage.apply {
            // Setting media player
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                setAudioFocusRequest(AudioManager.AUDIOFOCUS_NONE)
            }
            setVideoPath("android.resource://com.example.golush/" + R.raw.bg_main)
            setOnPreparedListener {
                it.isLooping = true
                it.setVolume(0f, 0f)
            }
            start()
        }

        binding.loginBtn.setOnClickListener { view: View ->
            Navigation.findNavController(view).apply {
                navigate(R.id.action_loginSignUp_to_login)
            }
        }

        binding.registerBtn.setOnClickListener { view: View ->
            Navigation.findNavController(view).apply {
                navigate(R.id.action_loginSignUp_to_register)
            }
        }
        return binding.root
    }


}
