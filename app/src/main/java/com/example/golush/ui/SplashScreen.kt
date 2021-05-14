package com.example.golush.ui

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View.TRANSLATION_Y
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.golush.R

class SplashScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val textLogo = findViewById<TextView>(R.id.logo)
        val textSub = findViewById<TextView>(R.id.sub_text)
        val alpha = PropertyValuesHolder.ofFloat("Alpha", 0f, 1f)
        val translateY = PropertyValuesHolder.ofFloat(TRANSLATION_Y, 100f)
        ObjectAnimator.ofPropertyValuesHolder(textLogo, alpha, translateY).apply {
            duration = 800
            start()
        }
        ObjectAnimator.ofPropertyValuesHolder(textSub, alpha, translateY).apply {
            duration = 800
            Handler().postDelayed({ start() }, 600)

        }

        // Will wait for 2 Sec and the go to the main activity
        Handler().postDelayed(
            // Basically will start a new activity and all remove this activity from the stack
            Runnable {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            },
            2000
        )
    }
}
