package com.example.simonsays

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.DialogFragment

class DeleteLeaderboard : DialogFragment(R.layout.fragment_delete_leaderboard) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val cancel : Button = view.findViewById(R.id.buttonCancel)
        val confirm : Button = view.findViewById(R.id.buttonConfirm)
        cancel.setOnClickListener {
            dismiss()
        }
        confirm.setOnClickListener {
            val db =PlayersProvider.DatabaseHelper(context).writableDatabase
            val strSQL = "DELETE FROM players"
            db?.execSQL(strSQL)
            Toast.makeText(context, "All records are deleted!", Toast.LENGTH_SHORT).show()
            dismiss()
        }
    }

}