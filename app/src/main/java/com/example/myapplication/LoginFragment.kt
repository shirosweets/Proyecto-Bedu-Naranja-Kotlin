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
    private lateinit var loginFormUser : TextInputLayout
    private lateinit var loginFormPassword : TextInputLayout
    private lateinit var loginButton : MaterialButton
    private lateinit var registerRedirectBtn : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loginFormUser = view.findViewById(R.id.loginFormEmail)
        loginFormPassword= view.findViewById(R.id.loginFormPassword)
        loginButton= view.findViewById(R.id.buttonLogIn)
        registerRedirectBtn= view.findViewById(R.id.buttonCheckIn)


        loginButton.setOnClickListener{
            val emailNotEmpty: Boolean = !loginFormUser.editText?.text.isNullOrEmpty()
            val passNotEmpty: Boolean = !loginFormUser.editText?.text.isNullOrEmpty()

            if(emailNotEmpty && passNotEmpty){
                Toast.makeText(
                    it.context,
                    "¡Bienvenido!",
                    Toast.LENGTH_SHORT
                ).show()
                findNavController().navigate(R.id.action_loginFragment2_to_menuActivity)
            }
            else{
                Toast.makeText(
                    it.context,getString(R.string.noticeIncompleteFields),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        registerRedirectBtn.setOnClickListener{
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