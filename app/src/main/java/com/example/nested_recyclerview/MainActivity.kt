package com.example.nested_recyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.nested_recyclerview.view.HomeMainFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentContainerView,HomeMainFragment())
            commit()
        }
    }
}