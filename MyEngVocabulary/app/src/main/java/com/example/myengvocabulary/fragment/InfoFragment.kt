package com.example.myengvocabulary.fragment

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.myengvocabulary.R
import com.example.myengvocabulary.viewmodel.DeckViewModel
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.util.*

class InfoFragment(val deckViewModel:DeckViewModel) : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {val view = inflater.inflate(R.layout.fragment_info,container,false)

        val format = SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
        val targetDate = format.parse("2020-10-23 00:00:00")
        var currentDate = Date()
        var milliSecond:Long
        var textString:String
        GlobalScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
                while (true) {
                    currentDate = Date()
                    milliSecond = targetDate.time - currentDate.time
                    if (milliSecond <= 0) {
                        view.findViewById<TextView>(R.id.textview_rml).text = ""
                        view.findViewById<TextView>(R.id.textview_d_day).text = ""
                        break
                    }
                    view.findViewById<TextView>(R.id.textview_d_day).text =
                        ((milliSecond) / (1000 * 24 * 60 * 60)).toString() + ":" +
                                ((milliSecond) % (1000 * 24 * 60 * 60) / (1000 * 60 * 60)) + ":" +
                                ((milliSecond) % (1000 * 24 * 60 * 60) % (1000 * 60 * 60) / (1000 * 60)) + ":" +
                                ((milliSecond) % (1000 * 24 * 60 * 60) % (1000 * 60 * 60) % (1000 * 60) / (1000))
                    delay(1000L)
                }
            }
        }.start()

        view.findViewById<Button>(R.id.button_reset).setOnClickListener{
            val firstDialog  = AlertDialog.Builder(context,R.style.DialogStyle)
            val firstDialogView = LayoutInflater.from(context).inflate(R.layout.dialog_delete_all,container,false)

            firstDialog.setView(firstDialogView).setPositiveButton("OK"){
                    first, _ ->
                first.dismiss()
                val dialogBuilder = AlertDialog.Builder(context)
                val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_progress,container,false)
                dialogBuilder.setCancelable(false)
                dialogBuilder.setView(dialogView)
                dialogView.findViewById<TextView>(R.id.textview_progress).text = "wait..."
                val dialog = dialogBuilder.create()
                dialog.show()
                deckViewModel.deleteAllDeck().invokeOnCompletion {
                    dialog.dismiss()
                }
                val intent = activity?.intent
                activity?.finish()
                startActivity(intent)
            }.setNegativeButton("Cancel"){
                    dialog, which ->
            }.show()

        }
        return view
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_info, container, false)
    }
}