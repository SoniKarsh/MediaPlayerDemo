package com.example.karshsoni.soundpool

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

class Main4Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main4)

        var action: String? = " "

        action = intent.extras!!.getString("DO")

        if (action != null) {
            when (action) {
                "Previous" -> Toast.makeText(this, "tapped", Toast.LENGTH_SHORT).show()
                "Play" -> Toast.makeText(this, "tapped", Toast.LENGTH_SHORT).show()
                "Next" -> Toast.makeText(this, "tapped", Toast.LENGTH_SHORT).show()
            }//Your code
            //Your code
        }
    }
}
