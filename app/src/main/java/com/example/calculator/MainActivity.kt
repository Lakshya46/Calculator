package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import com.example.calculator.databinding.ActivityMainBinding
import net.objecthunter.exp4j.Expression
import net.objecthunter.exp4j.ExpressionBuilder
import java.lang.ArithmeticException
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    var lastNumeric = false
    var stateError = false
    var lastDot = false
    var lastt = false

    private lateinit var expression: Expression
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun onAllclearClick (view : View){
        binding.dataText.text = ""
        binding.resultText.text = ""
        stateError = false
        lastDot = false
        lastNumeric = false
        lastt = false
        binding.resultText.visibility =View.GONE
    }

    fun onEqualClick(view: View) {
        OnEqual()
        binding.dataText.text = binding.resultText.text.toString().drop(1)
        if (binding.dataText.text.toString().contains(".")) {
            lastt = true
            lastNumeric = true
        } else lastt = false
    }

    fun OnDigitClick(view: View) {
        if (stateError) {
            if ((view as Button).text == ".") {
                binding.dataText.text = "0."
            } else {
                binding.dataText.text = (view as Button).text
                stateError = false
            }
        }
            else {

                if ((view as Button).text == "." && binding.dataText.text.toString().isEmpty()) {
                    binding.dataText.append("0.")
                    lastt = true
                    lastNumeric = true
                    }

            if ((view as Button).text == "." &&  !binding.dataText.text.toString().isEmpty() && !lastNumeric && !lastt) {
                binding.dataText.append("0.")
                lastt = true
                lastNumeric = true
            }
            if (!lastt && (view as Button).text == "." && lastNumeric &&  !binding.dataText.text.toString().isEmpty() ){
                binding.dataText.append(".")
                lastt = true
                lastNumeric = true
            }

            if (lastt && (view as Button).text == ".") {
                        binding.dataText.append("")}

            if ((view as Button).text != ".") {binding.dataText.append((view as Button).text)
            lastNumeric = true}
                    }

            OnEqual()


        }






    fun onOperatorClick(view: View){
        if (!stateError && lastNumeric){
            binding.dataText.append((view as Button).text)

            lastDot = false
            lastNumeric = false
            lastt = false
        }

    }



    fun onBackClick(view: View){
        binding.dataText.text = binding.dataText.text.toString().dropLast(1)
        try {
            var lastchar = binding.dataText.text.toString().last()

            if (lastchar.isDigit()){
                OnEqual()}
        }catch ( e : Exception){
            binding.resultText.text = ""
        binding.resultText.visibility = View.GONE
        Log.e("last char error", e.toString())
        }
    }
   fun onClearClick(view: View){
       binding.dataText.text = ""
       lastNumeric = false
   }

    fun OnEqual(){
        if (lastNumeric && !stateError){
            val txt = binding.dataText.text.toString()
            expression = ExpressionBuilder(txt).build()
            try {
                val result = expression.evaluate()
                binding.resultText.visibility =View.VISIBLE
                binding.resultText.text = "=" +result.toString()
            }
            catch (ex: ArithmeticException){
                Log.e("evaluate error" ,ex.toString())
                binding.resultText.text ="Error"
                stateError = true
                lastNumeric = false
                lastt = false


            }

        } }
}