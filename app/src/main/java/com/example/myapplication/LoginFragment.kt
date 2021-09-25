package com.example.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class LoginFragment : Fragment() {
    private lateinit var txtEmail : EditText
    private lateinit var txtPassword : EditText
    private lateinit var btnLogIn : Button
    private lateinit var btnCheckIn : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        txtEmail = view.findViewById(R.id.txtEmail)
        txtPassword= view.findViewById(R.id.txtPassword)
        btnLogIn= view.findViewById(R.id.buttonLogIn)
        btnCheckIn= view.findViewById(R.id.buttonCheckIn)


        btnLogIn.setOnClickListener{
            if(!txtEmail.text.isEmpty() && txtPassword.text.isNotEmpty()){
                val noticeWelcome = Toast.makeText(this.context,"¡Bienvenido ${txtEmail.getText()}!",
                    Toast.LENGTH_SHORT)
                noticeWelcome.show()}
            else{
                val noticeIncompleteFields = Toast.makeText(this.context,getString(R.string.noticeIncompleteFields),
                    Toast.LENGTH_SHORT)
                noticeIncompleteFields.show()}
        }

        btnCheckIn.setOnClickListener{
            val noticeCheckIn=
                Toast.makeText(this.context,getString(R.string.noticeCheckIn), Toast.LENGTH_SHORT)
            noticeCheckIn.show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }
}