package com.bbgo.module_home.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bbgo.module_home.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_home)
    }
}