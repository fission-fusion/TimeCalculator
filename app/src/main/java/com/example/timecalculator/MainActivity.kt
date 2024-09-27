package com.example.timecalculator

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var textInputFirst: EditText
    private lateinit var textInputSecond: EditText
    private lateinit var textResult: TextView
    private lateinit var buttonSum: Button
    private lateinit var buttonDiff: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        textInputFirst = findViewById(R.id.firstOperandET)
        textInputSecond = findViewById(R.id.secondOperandET)
        textResult = findViewById(R.id.resultTV)
        buttonSum = findViewById(R.id.sumBTN)
        buttonDiff = findViewById(R.id.diffBTN)

        buttonSum.setOnClickListener { onClick(it) }
        buttonDiff.setOnClickListener { onClick(it) }

    }

    fun timeToSecond(view: EditText): Int? {
        val matchResult = "(?:(?:(\\d+)h)?(\\d+)m)?(\\d+)s".toRegex().find(view.text)
        val hour = matchResult?.groups?.get(1)?.value?.toInt()
        val min = matchResult?.groups?.get(2)?.value?.toInt()
        val sec = matchResult?.groups?.get(3)?.value?.toInt()
        return if (sec != null) {
            (hour ?: 0) * 3600 + (min ?: 0) * 60 + sec
        } else null
    }

    fun sumOfTime(firstTimeText: EditText, secondTimeText: EditText): Int {
        val firstTime = timeToSecond(firstTimeText)
        val secondTime = timeToSecond(secondTimeText)
        return if (firstTime != null && secondTime != null) {
            firstTime + secondTime
        } else 0
    }

    fun diffOfTime(firstTimeText: EditText, secondTimeText: EditText): Int {
        val firstTime = timeToSecond(firstTimeText)
        val secondTime = timeToSecond(secondTimeText)
        return if (firstTime != null && secondTime != null) {
            firstTime - secondTime
        } else 0
    }

    fun convertToText(timeAsIntInput: Int): String {
        var timeAsInt = timeAsIntInput
        var result = ""
        if ((timeAsInt / 3600) > 0) {
            result = result + (timeAsInt / 3600).toString() + "h"
            timeAsInt %= 3600
            result = result + (timeAsInt / 60).toString() + "m"
            timeAsInt %= 60
            result = result + timeAsInt + "s"
        } else if ((timeAsInt / 60) > 0) {
            result = result + (timeAsInt / 60).toString() + "m"
            timeAsInt %= 60
            result = result + timeAsInt + "s"
        } else {
            result = result + timeAsInt + "s"
        }
        return result
    }

    fun onClick(view: View) {
        if (textInputFirst.text.isEmpty() || textInputSecond.text.isEmpty()) {
            return
        }

        var result = when (view.id) {
            R.id.sumBTN -> convertToText(sumOfTime(textInputFirst, textInputSecond))
            R.id.diffBTN -> convertToText(diffOfTime(textInputFirst, textInputSecond))
            else -> ""
        }
        textResult.text = result
    }
}