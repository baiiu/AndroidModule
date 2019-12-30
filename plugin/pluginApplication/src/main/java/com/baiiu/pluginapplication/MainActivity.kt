package com.baiiu.pluginapplication

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        one()
    }

    fun onClick(view: View) {
        startActivity(Intent(this, SecondActivity::class.java))
    }

    fun one() {
        two()
    }

    fun two() {
        three()
    }

    fun three() {

    }

}
