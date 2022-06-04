package com.example.simonsays

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class LeaderBoard : AppCompatActivity() {
    @SuppressLint("Range", "Recycle")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leader_board)
        val result: TextView = findViewById(R.id.textViewNameLB)
        val score : TextView = findViewById(R.id.textViewScoreLB)
        val back : Button = findViewById(R.id.buttonBackMM)
        val URL = "content://com.example.simonsays.PlayersProvider"
        val players = Uri.parse(URL)
        val rs = contentResolver.query(players, null, null, null, "Score DESC")
        val rs1 = contentResolver.query(players,null,null,null,"_ID DESC")
        var name =" "
        if(rs1!=null) {
            if (rs1.moveToFirst())
            {
                name=rs1.getString(rs1.getColumnIndex(PlayersProvider.NAME))
            }
        }
        if (rs != null) {
            if (rs.moveToFirst()) {
                do {
                    result.text = result.text.toString() + "\n" +
                            rs.getString(rs.getColumnIndex(PlayersProvider.NAME))
                            score.text=score.text.toString() +"\n" + rs.getString(rs.getColumnIndex(PlayersProvider.Score))

                } while (rs.moveToNext())
            }
        }
        back.setOnClickListener {
                val intent = Intent(this, MainMenu::class.java)
                intent.putExtra("name", name)
                startActivity(intent)
        }
    }
}