package com.example.kalkulatorminggu3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.example.kalkulatorminggu3.databinding.ActivityMainBinding
import java.lang.StringBuilder

class MainActivity : AppCompatActivity() {

    // mainCalc = main calculation textView
    // subCalc = textView above mainCalc used to see what last mainCalc was

    private lateinit var binding: ActivityMainBinding
    private lateinit var lastOperation: String
    private lateinit var savedSubCalc: String
    private var optDone: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        lastOperation = ""
        savedSubCalc = ""
    }


    fun inputNilai(view: View) {

        val inputtedValue = (view as Button).text

        with(binding)
        {
            // if user doesn't input 00 or 0 for mainCalc first digit (to prevent 0011, 01)
            // else do nothing
            if(!(mainCalc.text == "" && (inputtedValue == "00" || inputtedValue == "0"))) {

                // if no operation has been completed
                // add user input to mainCalc
                // else start everything anew
                if(!optDone) {

                    // if user hasn't inputted any operation to use
                    // add number to mainCalc
                    if(lastOperation == "") {
                        mainCalc.text = "${mainCalc.text}${inputtedValue}"
                    } else {

                        // if subCalc still empty
                        // make subCalc to mainCalc
                        // else add number to mainCalc
                        if(subCalc.text == "") {
                            subCalc.text = mainCalc.text
                            mainCalc.text = inputtedValue
                        } else {
                            mainCalc.text = "${mainCalc.text}${inputtedValue}"
                        }
                    }
                } else {
                    savedSubCalc = ""
                    subCalc.text = ""
                    mainCalc.text = inputtedValue
                    lastOperation = ""
                    optDone = false
                }
            }

        }

    }

    fun inputOperasi(view: View) {

        //
        var currentOperation = (view as Button).text.toString()

        with(binding)
        {

            if(!optDone){

                // if user hasn't inputted operation before this and current operation aren't clearing
                // save mainCalc to variable to used later in calculation
                //
                //
                if(lastOperation == "" && (currentOperation != "C" && currentOperation != "c")){
                    savedSubCalc = mainCalc.text.toString()
                    subCalc.text = "${mainCalc.text} $currentOperation"
                    mainCalc.text = ""
                    lastOperation = currentOperation
                } else if(currentOperation == "c") {

                    // if mainCalc isn't empty, clear it one by one
                    // else clear subCalc if there was any
                    if(mainCalc.text.toString() != "") {
                        clearOperationMini()
                    } else if(mainCalc.text.toString() == "" && subCalc.text != ""){
                        clearOperations()
                    }
                } else {

                    // if user want to calculate result, call doingOperation
                    // else if user want to change current operation
                    if(currentOperation == "=") {
                        doingOperations()
                    }  else if (subCalc.text.toString() == "" && currentOperation != "C"){
                        lastOperation = currentOperation
                        subCalc.text = "$savedSubCalc $currentOperation"
                    } else if(currentOperation == "C") {
                        clearOperations()
                    }
                }

            }  else {


                if(currentOperation != "C" && currentOperation != "c")
                {
                    savedSubCalc = mainCalc.text.toString()
                    subCalc.text = "${mainCalc.text} $currentOperation"
                    mainCalc.text = ""
                    lastOperation = currentOperation
                    optDone = false
                } else
                {
                    clearOperations()
                }

            }
        }

    }

    fun doingOperations() {

        with(binding){
            when(lastOperation){
                "+" -> mainCalc.text = (mainCalc.text.toString().toInt() + savedSubCalc.toInt()).toString()
                "-" -> mainCalc.text = (savedSubCalc.toInt() - mainCalc.text.toString().toInt()).toString()
                "X" -> mainCalc.text = (mainCalc.text.toString().toInt() * savedSubCalc.toInt()).toString()
                "/" -> mainCalc.text = (savedSubCalc.toInt() / mainCalc.text.toString().toInt()).toString()
                "%" -> mainCalc.text = (savedSubCalc.toInt() % mainCalc.text.toString().toInt()).toString()
            }
            subCalc.text = ""
            lastOperation = ""
            optDone = true
        }
    }

    fun clearOperations() {
        with(binding){
            savedSubCalc = ""
            subCalc.text = ""
            mainCalc.text = ""
            lastOperation = ""
        }
    }

    fun clearOperationMini(){
        with(binding){
            if(mainCalc.text.toString().length > 1)
            {
                var temp : StringBuilder = StringBuilder(mainCalc.text.toString())
                temp.deleteCharAt(temp.length - 1)
                mainCalc.text = temp.toString()
            } else if (subCalc.text != "") {
                mainCalc.text = ""
            }
            else {
                clearOperations()
            }
        }
    }
}