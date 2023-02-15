package com.example.quizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.example.quizapp.databinding.ActivityQuizBinding


class QuizActivity : AppCompatActivity() {
    private var selectedOption:Int?=null
    private lateinit var viewModel: QuizViewModel
    private lateinit var binding: ActivityQuizBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[QuizViewModel::class.java]
        getQuestions()
        getProgress()
        binding.progressbar.visibility = View.INVISIBLE
        binding.questionView.visibility = View.VISIBLE
        onSubmit()
        handleOptionSelect()
        buttonStateChange()
        handleCorrectAns()
    }
    private fun getQuestions(){
        viewModel.getCurrentQuestion().observe(this) {
            setQuestion(it)
        }
    }
    private fun getProgress(){
        viewModel.getQuestionsProgress().observe(this){
            binding.remainingQuestionTxt.text = "$it/${viewModel.getTotalQuestions()}"
            binding.questionsProgressBar.progress = ((it.toDouble()/viewModel.getTotalQuestions())*100).toInt()
        }
    }

   private fun setQuestion(question: Question){
        resetOptions()
        binding.questionTxt.text = question.question
        binding.flagImg.background = resources.getDrawable(question.imagePath,theme)
        binding.option1.text = question.option1
        binding.option2.text = question.option2
        binding.option3.text = question.option3
        binding.option4.text = question.option4
    }

    private fun onSubmit(){
        binding.submitBtn.setOnClickListener {
            when(viewModel.getButtonState().value){
                ButtonState.Submit -> {
                    selectedOption?.let {
                        viewModel.submitAnswer(optionNumber = it)
                    }
                }
                ButtonState.Finish -> {
                    startResultActivity()
                }
                ButtonState.NextQuestion -> {
                    viewModel.getNextQuestion()
                }
                null -> {

                }
            }
        }
    }

    private fun handleOptionSelect(){
        binding.option1.setOnClickListener {
            resetOptions()
            selectedOption=1
            binding.option1.setStrokeColorResource(R.color.purple_500)
        }
        binding.option2.setOnClickListener {
            resetOptions()
            selectedOption=2
            binding.option2.setStrokeColorResource(R.color.purple_500)
        }
        binding.option3.setOnClickListener {
            resetOptions()
            selectedOption=3
            binding.option3.setStrokeColorResource(R.color.purple_500)
        }
        binding.option4.setOnClickListener {
            resetOptions()
            selectedOption=4
            binding.option4.setStrokeColorResource(R.color.purple_500)
        }
    }

    private fun resetOptions(){
        selectedOption=null
        binding.option1.setStrokeColorResource(android.R.color.darker_gray)
        binding.option2.setStrokeColorResource(android.R.color.darker_gray)
        binding.option3.setStrokeColorResource(android.R.color.darker_gray)
        binding.option4.setStrokeColorResource(android.R.color.darker_gray)
        binding.option1.setBackgroundColor(resources.getColor(R.color.white,theme))
        binding.option2.setBackgroundColor(resources.getColor(R.color.white,theme))
        binding.option3.setBackgroundColor(resources.getColor(R.color.white,theme))
        binding.option4.setBackgroundColor(resources.getColor(R.color.white,theme))
        changeButtonClickOnOptions(enabled = true)
    }

    private fun buttonStateChange(){
        viewModel.getButtonState().observe(this){ it ->
            binding.submitBtn.text = it.btnText
        }
    }

    private fun handleCorrectAns(){
        viewModel.getCorrectAns().observe(this){
            it?.let{
                setCorrectOptionColor(it)
                if(it != selectedOption){
                    setWrongOptionColor(selectedOption!!)
                }

            }
        }
    }
    private fun setCorrectOptionColor(option:Int){
        changeButtonClickOnOptions(enabled=false)
        when(option){
            1 -> {
                binding.option1.setBackgroundColor(resources.getColor(android.R.color.holo_green_light,theme))
            }
            2 -> {
                binding.option2.setBackgroundColor(resources.getColor(android.R.color.holo_green_light,theme))
            }
            3 -> {
                binding.option3.setBackgroundColor(resources.getColor(android.R.color.holo_green_light,theme))
            }
            4 -> {
                binding.option4.setBackgroundColor(resources.getColor(android.R.color.holo_green_light,theme))

            }
        }
    }
    private fun setWrongOptionColor(option:Int){
        changeButtonClickOnOptions(enabled=false)
        when(option){
            1 -> {
                binding.option1.setBackgroundColor(resources.getColor(android.R.color.holo_red_light,theme))
            }
            2 -> {
                binding.option2.setBackgroundColor(resources.getColor(android.R.color.holo_red_light,theme))
            }
            3 -> {
                binding.option3.setBackgroundColor(resources.getColor(android.R.color.holo_red_light,theme))
            }
            4 -> {
                binding.option4.setBackgroundColor(resources.getColor(android.R.color.holo_red_light,theme))

            }
        }
    }
    private fun changeButtonClickOnOptions(enabled:Boolean){
        binding.option1.isEnabled=enabled
        binding.option2.isEnabled=enabled
        binding.option3.isEnabled=enabled
        binding.option4.isEnabled=enabled
    }

    private fun startResultActivity(){
        val intent:Intent = Intent(this,ResultActivity::class.java)
        intent.putExtra("userName",this.intent.extras?.getString("userName"))
        intent.putExtra("totalQuestions",viewModel.getTotalQuestions())
        intent.putExtra("correctAnswers",viewModel.getQuestionsProgress().value)
        startActivity(intent)
        finish()
    }

}