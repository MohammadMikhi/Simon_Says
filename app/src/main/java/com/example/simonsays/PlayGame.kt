package com.example.simonsays

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import android.widget.TextView
import java.util.*
import kotlin.concurrent.schedule

class PlayGame : AppCompatActivity() {
    var score = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play)
        val red : ImageView=findViewById(R.id.imageViewRed)
        val blue : ImageView=findViewById(R.id.imageViewBlue)
        val green : ImageView=findViewById(R.id.imageViewGreen)
        val yellow : ImageView=findViewById(R.id.imageViewYellow)
        val queue : Queue<String> =LinkedList()
        val tempQ : Queue<String> = LinkedList()
        var rand : Int
        var count : Long = 0
        val textScore : TextView=findViewById(R.id.textViewS)
        val gameOver = GameOverFragment()
        val db =PlayersProvider.DatabaseHelper(this).writableDatabase
        fun printQueue(){

            count=1
            queue.forEach {color ->
                Handler(Looper.getMainLooper()).postDelayed({when(color){
                    "red"-> {
                        count++
                        Timer().schedule(count*500){onOffRed(red)
                            }
                    }
                    "blue"->{
                        count++
                        Timer().schedule(count*500){onOffBlue(blue)
                            }
                    }
                    "green"->{
                        count++
                        Timer().schedule(count*500){onOffGreen(green)
                            }
                    }
                    "yellow"->{
                        count++
                        Timer().schedule(count*500){onOffYellow(yellow)
                            }
                    }

                }
                   count++
                                                            },500)

            }

        }
        fun startRound(){
            textScore.text= "Score: $score"

            queue.forEach {
                color -> tempQ.add(color)
            }

            rand = (0..3).shuffled().last()
            if(rand==0)
            {
                queue.add("red")
                tempQ.add("red")
            }
            if(rand==1)
            {
                queue.add("blue")
                tempQ.add("blue")
            }
            if(rand==2)
            {
                queue.add("green")
                tempQ.add("green")
            }
            if(rand==3)
            {
                queue.add("yellow")
                tempQ.add("yellow")
            }
            score++
            printQueue()
        }


        startRound()

        red.setOnClickListener {
            if(tempQ.poll()=="red") {
                onOffRed(red)
                if(tempQ.isEmpty())
                {
                    startRound()
                }
            }
            else{
                score--
                val bundle : Bundle? = intent.extras
                bundle?.let {
                    val name= bundle.getString("name")
                    val strSQL = "UPDATE players SET score=$score WHERE name=\"$name\""
                    db?.execSQL(strSQL)
                    gameOver.show(supportFragmentManager, "Game Over")
                }
            }
        }
        blue.setOnClickListener{
            if(tempQ.poll()=="blue") {
                onOffBlue(blue)
                if(tempQ.isEmpty())
                {
                    startRound()
                }
            }
            else{
                score--
                val bundle : Bundle? = intent.extras
                bundle?.let {
                    val name= bundle.getString("name")
                    val strSQL = "UPDATE players SET score=$score WHERE name=\"$name\""
                    db?.execSQL(strSQL)
                    gameOver.show(supportFragmentManager, "Game Over")
                }
            }
        }
        green.setOnClickListener{
            if(tempQ.poll()=="green"){
                onOffGreen(green)
                if(tempQ.isEmpty())
                {
                    startRound()
                }
            }
            else{
                score--
                val bundle : Bundle? = intent.extras
                bundle?.let {
                    val name= bundle.getString("name")
                    val strSQL = "UPDATE players SET score=$score WHERE name=\"$name\""
                    db?.execSQL(strSQL)
                    gameOver.show(supportFragmentManager, "Game Over")
                }
            }
        }
        yellow.setOnClickListener{
            if(tempQ.poll()=="yellow"){
                onOffYellow(yellow)
                if(tempQ.isEmpty())
                {
                    startRound()
                }
            }
            else{
                score--
                val bundle : Bundle? = intent.extras
                bundle?.let {
                    val name= bundle.getString("name")
                    val strSQL = "UPDATE players SET score=$score WHERE name=\"$name\""
                    db?.execSQL(strSQL)
                    gameOver.show(supportFragmentManager, "Game Over")
                }
            }
        }
    }


    private fun onOffRed(red : ImageView){
        red.setImageResource(R.drawable.red_on)
        Handler(Looper.getMainLooper()).postDelayed({red.setImageResource(R.drawable.red_dim)},700)
    }
    private fun onOffBlue(blue : ImageView){
            blue.setImageResource(R.drawable.blue_on)
            Handler(Looper.getMainLooper()).postDelayed({blue.setImageResource(R.drawable.blue_dim)},700)
    }
    private fun onOffGreen(green : ImageView){
        green.setImageResource(R.drawable.green_on)
        Handler(Looper.getMainLooper()).postDelayed({green.setImageResource(R.drawable.green_dim)},700)
    }
    private fun onOffYellow(yellow: ImageView){
            yellow.setImageResource(R.drawable.yellow_on)
            Handler(Looper.getMainLooper()).postDelayed({yellow.setImageResource(R.drawable.yellow_dim)},700)
    }
}