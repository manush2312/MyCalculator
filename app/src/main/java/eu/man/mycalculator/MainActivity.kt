package eu.man.mycalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.lang.ArithmeticException

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


    fun OnEquals(view : View){
        // we need to check that last number was a numeric
        if(lastNumeric){
            var tvValue = tvInput?.text.toString()  // this converts the tvInput to string and stores that into the tvValue
            var prefix = ""
            try {
                if(tvValue.startsWith("-")){
                    // this code will execute when the string starts with "-"
                    // this code will get the substring, ignoring the first position that is minus
                    prefix = "-"
                    tvValue = tvValue.substring(1) // gives the substring with ignoring the minus
                }

                if(tvValue.contains("-")){
                    val splitValue = tvValue.split("-")  // lets say we have "99-1" then it will split in the form of array -> 99  1

                    // we are now storing the value of splitValue in diff variables
                    var one = splitValue[0]  // this is also a string
                    var two = splitValue[1]  // this is also a string

                    if(prefix.isNotEmpty()){
                        one = prefix + one
                    }

                    var result = one.toDouble() - two.toDouble()  // we are converting one and two into double

                    tvInput?.text = removeZeroAfterResult(result.toString()) // we cannot assign double value to tvInput because it requires string as it is textView

                    // alternative way is:
                    // tvInput?.text = (one.toDouble() - two.toDouble()).toString()
                }else if(tvValue.contains("+")){
                    val splitValue = tvValue.split("+")

                    var one = splitValue[0]
                    var two = splitValue[1]

                    if(prefix.isNotEmpty()){
                        one = prefix + one
                    }

                    var result = one.toDouble() + two.toDouble()

                    tvInput?.text = removeZeroAfterResult(result.toString())

                }else if(tvValue.contains("*")){
                    val splitValue = tvValue.split("*")

                    var one = splitValue[0]
                    var two = splitValue[1]

                    if(prefix.isNotEmpty()){
                        one = prefix + one
                    }

                    var result = one.toDouble() * two.toDouble()

                    tvInput?.text = removeZeroAfterResult(result.toString())

                }else if(tvValue.contains("/")){
                    val splitValue = tvValue.split("/")

                    var one = splitValue[0]
                    var two = splitValue[1]

                    if(prefix.isNotEmpty()){
                        one = prefix + one
                    }

                    var result = one.toDouble() / two.toDouble()

                    tvInput?.text = removeZeroAfterResult(result.toString())
                }



            }catch (e : ArithmeticException){
                e.printStackTrace()
            }
        }
    }

    private fun removeZeroAfterResult(result : String) : String{
        var Value = result
        if(result.contains(".0")){
            Value = result.substring(0, result.length-2)
        }

        return Value
    }


    fun onOperator(view: View){   // this function checks whether there is an operator in the string or not
        // if there is an operator then we cannot add another operator..
        tvInput?.text?.let{
            if(lastNumeric && !isOperatorAdded(it.toString())){
                tvInput?.append((view as Button).text)
                lastNumeric = false
                lastDot = false
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