package com.example.golush.ui

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.ViewAnimationUtils
import android.view.ViewTreeObserver
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.example.golush.R
import com.example.golush.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private val navController by lazy { findNavController(R.id.nav_host_fragment) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.layoutMain.viewTreeObserver.apply {
            if (isAlive) {
                addOnGlobalLayoutListener(
                    object : ViewTreeObserver.OnGlobalLayoutListener {
                        override fun onGlobalLayout() {
                            binding.layoutMain.viewTreeObserver.removeOnGlobalLayoutListener(this)
                            reveal()
                        }
                    }
                )
            }
        }
        setContentView(binding.root)
        binding.bottomNavigation.setOnNavigationItemSelectedListener { item: MenuItem ->
            when (item.itemId) {
                R.id.menu_home -> {
                    navController.popBackStack(R.id.home4, false)
                    true
                }
                R.id.menu_fav -> {
                    navController.navigate(R.id.fav)
                    true
                }
                else -> {
                    false
                }
            }
        }
    }

    fun reveal() {
        val view: View = binding.layoutMain

        val cx = (view.left + view.right) / 2
        val cy = (view.top + view.bottom) / 2

        val finalRadius: Float = Math.max(view.width, view.height).toFloat()

        val anim = ViewAnimationUtils.createCircularReveal(view, cx, cy, 0f, finalRadius)
        anim.duration = 800
        view.visibility = View.VISIBLE

        anim.start()
    }

}
