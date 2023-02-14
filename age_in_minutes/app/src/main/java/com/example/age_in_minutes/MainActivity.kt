package com.example.age_in_minutes

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.age_in_minutes.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val dateFormat: SimpleDateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.let {
            title = "Age To Minutes"
        }
        binding.selectDateBtn.setOnClickListener{
            showDatePicker()

        }
    }

    private fun updateTime(pickedDate: LocalDate){
        val date = Date.from(Instant.ofEpochSecond(pickedDate.toEpochDay()*24*60*60))
        binding.selectedDateTxt.text =  dateFormat.format(date)
        binding.dateInMinutesTxt.text = (Instant.now().minusMillis(date.time).epochSecond/60).toString()
    }



    private fun showDatePicker(){
        val date = LocalDate.now()
        val dialog = DatePickerDialog(this,{
                _, year, month, day ->
            val pickedDate = LocalDate.of(year,month,day)
            updateTime(pickedDate)
        },date.year,date.monthValue,date.dayOfMonth)
        dialog.datePicker.maxDate = Instant.now().toEpochMilli()
        dialog.show()
    }
}
