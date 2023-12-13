package com.example.homework_17.common

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.homework_17.R
import com.example.homework_17.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}