package com.example.simonsays

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.DialogFragment

class GameOverFragment : DialogFragment(R.layout.fragment_game_over) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val scoreTv : TextView=view.findViewById(R.id.textViewScoreFragment)
        val playAgain : Button =view.findViewById(R.id.buttonPlayAgain)
        val leaderBtn : Button=view.findViewById(R.id.buttonLeader)
        val m1 : PlayGame = activity as PlayGame
        scoreTv.text= "Score: "+ m1.score
        playAgain.setOnClickListener {
            m1.recreate()
            dismiss()
        }
        leaderBtn.setOnClickListener {
            val intent= Intent(context, LeaderBoard::class.java)
            startActivity(intent)
            dismiss()
        }

    }


}