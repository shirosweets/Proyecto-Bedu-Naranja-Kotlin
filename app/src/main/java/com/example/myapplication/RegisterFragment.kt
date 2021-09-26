package com.example.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputLayout

class RegisterFragment : Fragment() {
    private lateinit var registerButton : MaterialButton
    private lateinit var regFormUser : TextInputLayout
    private lateinit var regFormEmail : TextInputLayout
    private lateinit var regFormPhone : TextInputLayout
    private lateinit var regFormPassword : TextInputLayout
    private val transitionOptions = navOptions {
        anim {
            enter = R.anim.slide_in_left
            exit = R.anim.slide_out_right
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    private val isFormValid: () -> Boolean = {
        !(regFormUser.editText?.text.isNullOrEmpty() ||
        regFormEmail.editText?.text.isNullOrEmpty() ||
        regFormPhone.editText?.text.isNullOrEmpty() ||
        regFormPassword.editText?.text.isNullOrEmpty())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerButton = view.findViewById(R.id.registerButton)
        regFormUser = view.findViewById(R.id.regFormUser)
        regFormEmail = view.findViewById(R.id.regFormEmail)
        regFormPhone = view.findViewById(R.id.regformPhoneNumber)
        regFormPassword = view.findViewById(R.id.regFormPassword)

        registerButton.setOnClickListener {
            if(isFormValid()) {
                findNavController().navigate(
                    R.id.action_registerFragment2_to_loginFragment2,
                    null,
                    transitionOptions
                )
            } else {
                Toast.makeText(
                    it.context,
                    getString(R.string.noticeIncompleteFields),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    }
}