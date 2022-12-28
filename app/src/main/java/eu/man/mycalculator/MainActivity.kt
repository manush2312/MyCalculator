package eu.man.mycalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private var tvInput: TextView? = null
    var lastNumeric : Boolean = false
    var lastDot : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvInput = findViewById(R.id.tvInput)
    }

    fun onDigit(view: View){
        tvInput?.append((view as Button).text)  // I cannot write view.text here as view does not
        // text property but button has the text property, so i need to change the the view as button.


        // this is to make sure that whenever a digit is pressed then lastNumeric is true and Dot is false..
        lastNumeric = true
        lastDot = false
    }

    fun OnClear (view: View){
        tvInput?.text = ""
    }

    fun OnDecimalPoint(view: View){
        // important point to note here was that this should only work when last entry is not a dot
        // if last entry was a dot then this should not work..

        if(lastNumeric && !lastDot){
            tvInput?.append(".")
            lastNumeric = false
            lastDot = true
        }
    }


    fun onOperator(view: View){
        tvInput?.text?.let{
            if(lastNumeric && !isOperatorAdded(it.toString())){
                tvInput?.append((view as Button).text)
            }
        }
    }


    private fun isOperatorAdded(value: String)  : Boolean{  // in this we are passing string whose
    // name is value and return type is Boolean

        return if(value.startsWith("-")){
            false
        }else{
            value.contains("/") || value.contains("*") || value.contains("+") || value.contains("-")
        }


    }
}