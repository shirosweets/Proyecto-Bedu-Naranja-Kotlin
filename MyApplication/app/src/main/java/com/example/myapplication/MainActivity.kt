package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    private lateinit var txtEmail : EditText
    private lateinit var txtPassword : EditText
    private lateinit var btnIniciarSesion : Button
    private lateinit var btnRegistrarse : Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        txtEmail = findViewById(R.id.txtEmail)
        txtPassword= findViewById(R.id.txtPassword)
        btnIniciarSesion= findViewById(R.id.buttonIniciarSesion)
        btnRegistrarse= findViewById(R.id.buttonRegistrarse)

        txtEmail.nextFocusDownId
        txtPassword.nextFocusDownId


        btnIniciarSesion.setOnClickListener{
            if(!txtEmail.text.isEmpty() && !txtPassword.text.isEmpty()){
                val aviso1 = Toast.makeText(applicationContext,"¡Bienvenido ${txtEmail.getText()}!",Toast.LENGTH_SHORT)
                aviso1.show()}
            else{
                val aviso2 = Toast.makeText(applicationContext,"Por favor, complete todos los campos",Toast.LENGTH_SHORT)
                aviso2.show()}
        }

        btnRegistrarse.setOnClickListener{
            val aviso=Toast.makeText(applicationContext,getString(R.string.registrarse),Toast.LENGTH_SHORT)
            aviso.show()
        }

    }
}



