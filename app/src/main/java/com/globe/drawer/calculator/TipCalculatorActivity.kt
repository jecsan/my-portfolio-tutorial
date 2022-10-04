package com.globe.drawer.calculator

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.globe.drawer.R

// Extensions:
// Create a TipCalculator class for computations
// Add a tip description, display text based on tip percent
// Bonus: Change color based on tip percent


//Activity, activity xml, strings/colors/etc


class TipCalculatorActivity : AppCompatActivity() {

    private lateinit var etAmount: EditText
    private lateinit var seekBar: SeekBar
    private lateinit var tvTipPercent: TextView
    private lateinit var tvTip: TextView
    private lateinit var tvTotal: TextView
    private lateinit var tipDescription: TextView

    private val tipCalculator = TipCalculator()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculator)

        etAmount = findViewById(R.id.etAmount)
        seekBar = findViewById(R.id.seekBar)
        tvTipPercent = findViewById(R.id.tvTipPercent)
        tvTip = findViewById(R.id.tvTip)
        tvTotal = findViewById(R.id.tvTotal)
        tipDescription = findViewById(R.id.tvTipDescription)

        addEditTextListener()
        addSeekbarListener()

        supportActionBar?.title = "Tip Calculator"
    }


    private fun addEditTextListener() {
        etAmount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                computeAndDisplayTip()
            }
        })

    }

    private fun addSeekbarListener() {
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                tvTipPercent.text = "$progress%"
                computeAndDisplayTip()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })
    }


    private fun computeAndDisplayTip() {

        val amount: Double = etAmount.text.toString().toDoubleOrNull() ?: 0.0
        val percentage: Int = seekBar.progress

        tipCalculator.computeTip(amount, percentage)

        displayTip()
    }


    private fun displayTip() {
        tvTip.text = "%.2f".format(tipCalculator.tip)
        tvTotal.text = "%.2f".format(tipCalculator.total)

        val description = when (seekBar.progress) {
            in 0..9 -> {
                "Poor"
            }
            in 10..15 -> {
                "Good"
            }
            in 16..20 -> {
                "Great"
            }
            else -> {
                "Generous"
            }
        }

        tipDescription.text = description

        updateTipColor()
    }

    private fun updateTipColor() {
        val descriptionColor = when (seekBar.progress) {
            in 0..9 -> {
                Color.RED
            }
            in 10..15 -> {
                Color.parseColor("#000000")
            }
            in 16..20 -> {
                Color.YELLOW
            }
            else -> {
                Color.GREEN
            }
        }
        tipDescription.setTextColor(descriptionColor)
    }


    // Initialize project as repo
    // Add github origin
    // Commit all, set upstream, push

    // git init
    // git add --all
    // git commit -a -m "Initial project"
    // git remote add tip-calculator git@github.com:username
    // git push --set-upstream origin master
}