package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.example.myapplication.UserVipApplication.Companion.prefs
import com.example.myapplication.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {

    lateinit var binding: ActivityResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUI()
    }

    private fun initUI() {
        binding.bCloseSession.setOnClickListener {
            prefs.wipe()
            onBackPressed()
        }

        val userName = prefs.getName()
        binding.tvWelcomeUser.text = "Welcome $userName"

        if(prefs.getVip()) {
            setVipColorBackground()
        }
    }

    private fun setVipColorBackground() {
        binding.llContainer.setBackgroundColor(ContextCompat.getColor(this, R.color.goldVip))
    }
}