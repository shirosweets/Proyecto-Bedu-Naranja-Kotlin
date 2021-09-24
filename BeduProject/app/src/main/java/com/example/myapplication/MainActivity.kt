package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    private lateinit var txtEmail : EditText
    private lateinit var txtPassword : EditText
    private lateinit var btnLogIn : Button
    private lateinit var btnCheckIn : Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        txtEmail = findViewById(R.id.txtEmail)
        txtPassword= findViewById(R.id.txtPassword)
        btnLogIn= findViewById(R.id.buttonLogIn)
        btnCheckIn= findViewById(R.id.buttonCheckIn)


        btnLogIn.setOnClickListener{
            if(!txtEmail.text.isEmpty() && !txtPassword.text.isEmpty()){
                val noticeWelcome = Toast.makeText(applicationContext,"¡Bienvenido ${txtEmail.getText()}!",Toast.LENGTH_SHORT)
                noticeWelcome.show()}
            else{
                val noticeIncompleteFields = Toast.makeText(applicationContext,getString(R.string.noticeIncompleteFields),Toast.LENGTH_SHORT)
                noticeIncompleteFields.show()}
        }

        btnCheckIn.setOnClickListener{
            val noticeCheckIn=Toast.makeText(applicationContext,getString(R.string.noticeCheckIn),Toast.LENGTH_SHORT)
            noticeCheckIn.show()
        }

    }
}



