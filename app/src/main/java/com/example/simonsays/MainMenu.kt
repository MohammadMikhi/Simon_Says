package com.example.simonsays

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainMenu : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)
        val btnStart : Button = findViewById(R.id.buttonStart)
        val btnLeaderBoard: Button= findViewById(R.id.buttonLeaderboard)
        btnStart.setOnClickListener {
            val bundle : Bundle? = intent.extras
            bundle?.let {
                val intent1 = Intent(this, PlayGame::class.java)
                intent1.putExtra("name",bundle.getString("name"))
                startActivity(intent1)
            }
        }
        btnLeaderBoard.setOnClickListener {
            val bundle : Bundle? = intent.extras
            bundle?.let {
                val intent1 = Intent(this, LeaderBoard::class.java)
                intent1.putExtra("name",bundle.getString("name"))
                startActivity(intent1)
            }
        }

    }
}