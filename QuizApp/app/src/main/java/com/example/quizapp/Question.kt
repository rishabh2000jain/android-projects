package com.example.quizapp

data class Question(
    val questionNumber: Int,
    val question: String,
    val imagePath: Int,
    val option1: String,
    val option2: String,
    val option3: String,
    val option4: String,
    val ansOption:Int
)
