package com.example.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputLayout

class LoginFragment : Fragment() {
    private lateinit var txtEmail : TextInputLayout
    private lateinit var txtPassword : TextInputLayout
    private lateinit var btnLogIn : MaterialButton
    private lateinit var btnCheckIn : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        txtEmail = view.findViewById(R.id.loginFormEmail)
        txtPassword= view.findViewById(R.id.loginFormPassword)
        btnLogIn= view.findViewById(R.id.buttonLogIn)
        btnCheckIn= view.findViewById(R.id.buttonCheckIn)


        btnLogIn.setOnClickListener{
            val emailNotEmpty: Boolean = txtEmail.editText?.text.isNullOrEmpty()
            val passNotEmpty: Boolean = txtEmail.editText?.text.isNullOrEmpty()

            if(emailNotEmpty && passNotEmpty){
                val noticeWelcome = Toast.makeText(this.context,"¡Bienvenido!",
                    Toast.LENGTH_SHORT)
                noticeWelcome.show()}
            else{
                val noticeIncompleteFields = Toast.makeText(this.context,getString(R.string.noticeIncompleteFields),
                    Toast.LENGTH_SHORT)
                noticeIncompleteFields.show()}
        }

        btnCheckIn.setOnClickListener{
            findNavController().navigate(R.id.action_loginFragment2_to_registerFragment2, null)
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