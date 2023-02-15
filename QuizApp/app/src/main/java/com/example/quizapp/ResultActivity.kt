package com.example.quizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.quizapp.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {
    lateinit var binding: ActivityResultBinding
    private var userName:String?=null
    private var totalQuestions:Int?=null
    private var correctAnswers:Int?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
        intent.extras?.let {
            userName = it.getString("userName","")
            totalQuestions = it.getInt("totalQuestions",)
            correctAnswers = it.getInt("correctAnswers",)
        }
        binding.userNameTxt.text = userName
        binding.scoreDetailTxt.text = binding.scoreDetailTxt.text.toString().replace(mapOf<String,Any>(Pair("@correctAnswers",correctAnswers!!),Pair("@totalQuestions",totalQuestions!!)));
        binding.finishBtn.setOnClickListener {
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }
    }
}

fun String.replace(keyValue:Map<String,Any>):String{
    var newString:String = this
    keyValue.forEach{
        newString = newString.replaceFirst(Regex(it.key),it.value.toString())
    }
    return newString
}