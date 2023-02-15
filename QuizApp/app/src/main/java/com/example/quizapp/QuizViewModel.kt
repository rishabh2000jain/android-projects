package com.example.quizapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class QuizViewModel: ViewModel() {
    private var totalQuestion:Int = 0
    private val correctAnswerCount:MutableLiveData<Int> = MutableLiveData(0)
    private val correctAnswer:MutableLiveData<Int?> = MutableLiveData(null)
    private val currentQuestion:MutableLiveData<Question> = MutableLiveData()
    private var buttonState:MutableLiveData<ButtonState> =MutableLiveData(ButtonState.Submit)
    init {
        currentQuestion.value = Constants.getQuestions()[0]
        totalQuestion = Constants.getQuestions().size
    }

    fun getCurrentQuestion():LiveData<Question>{
        return currentQuestion
    }
    fun getQuestionsProgress():LiveData<Int>{
        return correctAnswerCount
    }
    fun  getTotalQuestions():Int{
        return totalQuestion
    }
    fun  getCorrectAns():LiveData<Int?>{
        return correctAnswer
    }

    fun submitAnswer(optionNumber:Int){
        currentQuestion.value?.let {
            if(it.ansOption == optionNumber){
                correctAnswerCount.value  = correctAnswerCount.value?.plus(1)
            }
            correctAnswer.value = it.ansOption
            currentQuestion.value?.let {
                if(it.questionNumber == Constants.getQuestions().size){
                    updateButtonState(ButtonState.Finish)
                    return
                }else{
                    updateButtonState(ButtonState.NextQuestion)
                }
            }
        }

    }

    fun getButtonState():LiveData<ButtonState> = buttonState

     fun getNextQuestion(){
         correctAnswer.value=null
        currentQuestion.value?.let {
            if(it.questionNumber == Constants.getQuestions().size){
                updateButtonState(ButtonState.Finish)
                return
            }
            currentQuestion.value = Constants.getQuestions()[it.questionNumber]
            updateButtonState(ButtonState.Submit)
        }
    }


    private fun updateButtonState(type:ButtonState){
        buttonState.value = type
    }

}

enum class ButtonState(val btnText:String){
    Submit("Submit"),
    Finish("Finish"),
    NextQuestion("Next Question")
}