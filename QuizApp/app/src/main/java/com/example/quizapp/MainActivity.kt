package com.example.quizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation
import androidx.core.widget.addTextChangedListener
import com.example.quizapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.userNameTxt.addTextChangedListener {
            if(it.isNullOrBlank()){
                disableButton()
            }else{
                enableButton()
            }

        }
        binding.startBtn.setOnClickListener{
            val intent  = Intent(this,QuizActivity::class.java)
            intent.putExtra("userName",binding.userNameTxt.text.toString())
            startActivity(intent)
            finish()
        }
    }
    private fun disableButton(){
        binding.startBtn.isEnabled = false
        binding.startBtn.setBackgroundColor(resources.getColor(android.R.color.darker_gray,theme))
    }
    private fun enableButton(){
        binding.startBtn.isEnabled = true
        binding.startBtn.setBackgroundColor(resources.getColor(R.color.purple_500,theme))

    }
}