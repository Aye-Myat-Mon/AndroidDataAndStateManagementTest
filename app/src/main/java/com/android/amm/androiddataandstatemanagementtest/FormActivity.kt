package com.android.amm.androiddataandstatemanagementtest

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_form.*
import java.text.SimpleDateFormat
import java.util.*

class FormActivity : AppCompatActivity() {
    private val myCalendar: Calendar = Calendar.getInstance()
    private var userAge: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)

        chooseDOB()

        ib_back.setOnClickListener {
            finish()
        }

        btn_create_acc.setOnClickListener {
            validateFirstName()
            validateLastName()
            validateEmail()
            validatePhoneNumber()
            validateDob()
            validateNationality()
            validateResidence()
            if (!til_first_name.isErrorEnabled &&
                !til_last_name.isErrorEnabled &&
                !til_email.isErrorEnabled &&
                !til_dob.isErrorEnabled &&
                !til_residence.isErrorEnabled &&
                !til_nationality.isErrorEnabled &&
                !til_mobile_no.isErrorEnabled ) {
                Toast.makeText(this, "Successfully created!", Toast.LENGTH_LONG).show()
            }
        }

        // Save switch state in shared preferences
        val sharedPreferences = getSharedPreferences("save", MODE_PRIVATE)
        sc_gender.isChecked = sharedPreferences.getBoolean("value", true)

        sc_gender.setOnClickListener {
            if (sc_gender.isChecked) {
                val editor = getSharedPreferences("save", MODE_PRIVATE).edit()
                editor.putBoolean("value", true)
                editor.apply()
                sc_gender.isChecked = true
            } else {
                val editor = getSharedPreferences("save", MODE_PRIVATE).edit()
                editor.putBoolean("value", false)
                editor.apply()
                sc_gender.isChecked = false
            }
        }
    }

    private fun chooseDOB() {
        val date =
            OnDateSetListener { _, year, month, day ->
                myCalendar.set(Calendar.YEAR, year)
                myCalendar.set(Calendar.MONTH, month)
                myCalendar.set(Calendar.DAY_OF_MONTH, day)
                val myFormat = "dd/mm/yy"
                val dateFormat = SimpleDateFormat(myFormat, Locale.US)
                et_dob.setText(dateFormat.format(myCalendar.time))
                userAge = year
            }
        et_dob.setOnClickListener {
            DatePickerDialog(
                this,
                date,
                myCalendar.get(Calendar.YEAR),
                myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
    }

    private fun validateFirstName(): Boolean {
        val firstName = et_first_name.text.toString().trim()
        return if (firstName.isEmpty()) {
            til_first_name.error = "Field can not be empty"
            false
        } else {
            til_first_name.error = null
            til_first_name.isErrorEnabled = false
            true
        }
    }

    private fun validateDob(): Boolean {
        val dob = et_dob.text.toString().trim()
        val currentYear = Calendar.getInstance()[Calendar.YEAR]
        val isAgeValid = currentYear - userAge
        return when {
            dob == "DD/MM/YY" -> {
                til_dob.error = "Please choose date of birth"
                false
            }
            isAgeValid < 14 -> {
                til_dob.error = "You are not eligible to apply"
                false
            }
            else -> {
                til_dob.error = null
                til_dob.isErrorEnabled = false
                true
            }
        }
    }

    private fun validateLastName(): Boolean {
        val lastName = et_last_name.text.toString().trim()
        return if (lastName.isEmpty()) {
            til_last_name.error = "Field can not be empty"
            false
        } else {
            til_last_name.error = null
            til_last_name.isErrorEnabled = false
            true
        }
    }

    private fun validateEmail(): Boolean {
        val email = et_email.text.toString().trim()
        val checkEmail = "[a-zA-Z0-9._-]+@[a-z]+.+[a-z]+"
        return when {
            email.isEmpty() -> {
                til_email.error = "Field can not be empty"
                false
            }
            !email.matches(checkEmail.toRegex()) -> {
                til_email.error = "Invalid Email!"
                false
            }
            else -> {
                til_email.error = null
                til_email.isErrorEnabled = false
                true
            }
        }
    }

    private fun validatePhoneNumber(): Boolean {
        val phoneNo = et_mobile_no.text.toString().trim()
        return when {
            phoneNo.isNotEmpty() && phoneNo.length < 7 -> {
                til_mobile_no.error = "Enter valid phone number"
                false
            }
            else -> {
                til_mobile_no.error = null
                til_mobile_no.isErrorEnabled = false
                true
            }
        }
    }

    private fun validateNationality(): Boolean {
        val nationality = et_nationality.text.toString().trim()
        return if (nationality.isEmpty()) {
            til_nationality.error = "Field can not be empty"
            false
        } else {
            til_nationality.error = null
            til_nationality.isErrorEnabled = false
            true
        }
    }

    private fun validateResidence(): Boolean {
        val residence = et_residence.text.toString().trim()
        return if (residence.isEmpty()) {
            til_residence.error = "Field can not be empty"
            false
        } else {
            til_residence.error = null
            til_residence.isErrorEnabled = false
            true
        }
    }
}