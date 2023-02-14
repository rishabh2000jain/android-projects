package com.example.calculatorapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.calculatorapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding
    private var operation:Char? = null
    var foundResult:Boolean=false
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        appendText("0")
        handleClickEvents()
    }
    private fun handleClickEvents(){
        binding.apply {
            btn0.setOnClickListener {
               appendText("0")
            }
            btn1.setOnClickListener {
                appendText("1")
            }
            btn2.setOnClickListener {
                appendText("2")
            }
            btn3.setOnClickListener {
                appendText("3")
            }
            btn4.setOnClickListener {
                appendText("4")
            }
            btn5.setOnClickListener {
                appendText("5")
            }
            btn6.setOnClickListener {
                appendText("6")
            }
            btn7.setOnClickListener {
                appendText("7")
            }
            btn8.setOnClickListener {
                appendText("8")
            }
            btn9.setOnClickListener {
                appendText("9")
            }
            btnAdd.setOnClickListener{
                setOperator('+')
            }
            btnSubtract.setOnClickListener{
                setOperator('-')
            }
            btnDivide.setOnClickListener{
                setOperator('/')
            }
            btnMultiply.setOnClickListener{
                setOperator('*')
            }
            btnClr.setOnClickListener{
                reset()
            }
            btnDot.setOnClickListener{
                appendText(".")
            }
            btnEqual.setOnClickListener{

                val number1 = binding.txtFirstOperand.text.toString().toDoubleOrNull()
                val number2 = binding.txtSecondOperand.text.toString().toDoubleOrNull()
                if(number1==null || number2 == null) return@setOnClickListener;
                when (operation) {
                    '+' -> {
                        clearSecondText()
                        appendText((number1+number2).toString())
                    }
                    '-' -> {
                        clearSecondText()
                        appendText((number1-number2).toString())
                    }
                    '*' -> {
                        clearSecondText()
                        appendText((number1*number2).toString())
                    }
                    '/' -> {
                        clearSecondText()
                        appendText((number1/number2).toString())
                    }
                    else -> {}
                }
                clearFirstText()
                foundResult=true
                clearOperator()
            }
        }
    }
    private fun appendText(text:String){
        if(text == "." && binding.txtSecondOperand.text.contains(".")){
            return
        }
        if(foundResult){
            foundResult=false
            clearSecondText()
        }
        if (binding.txtSecondOperand.text.equals("0")){
            binding.txtSecondOperand.text = text
        }else{
            binding.txtSecondOperand.text = binding.txtSecondOperand.text.toString().plus(text)
        }
    }
    private fun clearSecondText(){
        binding.txtSecondOperand.text = "0"
    }
    private fun clearFirstText(){
        binding.txtFirstOperand.text = ""
    }
    private fun reset(){
        clearSecondText()
        clearFirstText()
       clearOperator()
    }
    private fun setOperator(operator:Char){
        if(binding.txtFirstOperand.text.isNotBlank()){
            return
        }
        if(operation==null){
            updateReplaceOperand()
        }
        this.operation = operator
        binding.txtOperator.text = operator.toString()
    }
    private fun updateReplaceOperand(){
        binding.txtFirstOperand.text = binding.txtSecondOperand.text.toString()
        clearSecondText()
    }
    private fun clearOperator(){
        operation = null
        binding.txtOperator.text = operation?.toString().orEmpty()
    }

}