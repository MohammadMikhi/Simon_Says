package com.example.simonsays

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatDelegate

class LoginPage : AppCompatActivity() {
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_page)
        val login: Button = findViewById(R.id.LogIN)
        val score  = 0
        val switch : Switch = findViewById(R.id.switchMode)
        val delete : TextView = findViewById(R.id.textViewDelete)
        val deleteLeaderboard = DeleteLeaderboard()
        login.setOnClickListener{
            val records = ContentValues()
            records.put(PlayersProvider.NAME, (findViewById<View>(R.id.Name) as EditText).text.toString())
            records.put(PlayersProvider.Score, score)
            contentResolver.insert(PlayersProvider.CONTENT_URI, records)
            val intent= Intent(this, MainMenu::class.java)
            intent.putExtra("name", (findViewById<View>(R.id.Name) as EditText).text.toString())
            startActivity(intent)
        }
        switch.setOnCheckedChangeListener { _, b ->
            if(b){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                delegate.applyDayNight()
            }
            else
            {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                delegate.applyDayNight()
            }
        }
        delete.setOnClickListener {
            deleteLeaderboard.show(supportFragmentManager,"Delete Leaderboard")
        }
    }
}