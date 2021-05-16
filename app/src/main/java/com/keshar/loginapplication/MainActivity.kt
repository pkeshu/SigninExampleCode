package com.keshar.loginapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import com.keshar.loginapplication.data.UserPreferences
import com.keshar.loginapplication.ui.auth.AuthActivity
import com.keshar.loginapplication.ui.home.HomeActivity
import com.keshar.loginapplication.ui.startNewActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val userPreferences = UserPreferences(this)
        userPreferences.authToken.asLiveData().observe(this,
            Observer {
                val activity =
                    if (it == null) AuthActivity::class.java else HomeActivity::class.java
                startNewActivity(activity)
            })

    }
}