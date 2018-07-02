package com.example.karshsoni.soundpool

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main2.*
import kotlinx.android.synthetic.main.activity_main3.*

class Main3Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)

        var inputString = inputString.text.toString()

        btnStartService.setOnClickListener {
            var serviceIntent = Intent(this, ExampleService::class.java)
            serviceIntent.putExtra("Name", inputString)
            startService(serviceIntent)
        }

        btnStopService.setOnClickListener {
            var serviceIntent = Intent(this, ExampleService::class.java)
            stopService(serviceIntent)
        }

    }
}
