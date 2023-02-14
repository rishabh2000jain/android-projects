package com.example.quizapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class QuizViewModel: ViewModel() {
    var userName:String? = null
    companion object{
        val Factory : ViewModelProvider.Factory = object :ViewModelProvider.Factory{
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return QuizViewModel() as T
            }
        }
    }
}