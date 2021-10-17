package com.example.myapplication

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.navigation.Navigation
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class RegisterFragment : Fragment() {
    private lateinit var registerButton : MaterialButton
    private lateinit var regFormUser : TextInputLayout
    private lateinit var regFormEmail : TextInputLayout
    private lateinit var regFormPhone : TextInputLayout
    private lateinit var regFormPassword : TextInputLayout
    private lateinit var nameInputText : TextInputEditText
    private lateinit var emailInputText : TextInputEditText
    private lateinit var phoneInputText : TextInputEditText
    private lateinit var passwordInputText : TextInputEditText
    private var sharedPreferences : SharedPreferences? = null


    private val isFormValid: () -> Boolean = {
        !(regFormUser.editText?.text.isNullOrEmpty() ||
                regFormEmail.editText?.text.isNullOrEmpty() ||
                regFormPhone.editText?.text.isNullOrEmpty() ||
                regFormPassword.editText?.text.isNullOrEmpty())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerButton = view.findViewById(R.id.registerButton)
        regFormUser = view.findViewById(R.id.regFormUser)
        regFormEmail = view.findViewById(R.id.regFormEmail)
        regFormPhone = view.findViewById(R.id.regformPhoneNumber)
        regFormPassword = view.findViewById(R.id.regFormPassword)

        nameInputText = view.findViewById(R.id.nameInputText)
        emailInputText = view.findViewById(R.id.emailInputText)
        phoneInputText = view.findViewById(R.id.phoneInputText)
        passwordInputText = view.findViewById(R.id.passwordInputText)

        sharedPreferences = requireContext().getSharedPreferences(
            getString(R.string.loginSharedPreferenceFile),
            Context.MODE_PRIVATE
        )

        setTextChangeActions()
        setClickListeners(view)

    }


    private fun setTextChangeActions(){
        nameInputText.doOnTextChanged { text,_,_,_ ->
            if(text!!.isNotEmpty()){
                regFormUser.error = null
            }
        }
        emailInputText.doOnTextChanged { text,_,_,_ ->
            if(text!!.isNotEmpty()){
                regFormEmail.error = null
            }
        }
        phoneInputText.doOnTextChanged { text,_,_,_ ->
            if(text!!.isNotEmpty()){
                regFormPhone.error = null
            }
        }
        passwordInputText.doOnTextChanged { text,_,_,_ ->
            if(text!!.isNotEmpty()){
                regFormPassword.error = null
            }
        }
    }

    private fun setClickListeners(view: View){
        registerButton.setOnClickListener {
            if(isFormValid()) {

                sharedPreferences?.edit()
                    ?.putString("USER_EMAIL", emailInputText.text.toString())
                    ?.putString("USER_PASSWORD", passwordInputText.text.toString())
                    ?.apply()


                val action = RegisterFragmentDirections.actionRegisterFragment2ToLoginFragment2()
                Navigation.findNavController(view).navigate(action)

            } else {
                if(nameInputText.text.isNullOrEmpty()){regFormUser.error=getString(R.string.noticeIncompleteField)}
                if(emailInputText.text.isNullOrEmpty()){regFormEmail.error=getString(R.string.noticeIncompleteField)}
                if(phoneInputText.text.isNullOrEmpty()){regFormPhone.error=getString(R.string.noticeIncompleteField)}
                if(passwordInputText.text.isNullOrEmpty()){regFormPassword.error=getString(R.string.noticeIncompleteField)}

                Snackbar.make(view, getString(R.string.noticeIncompleteFields), Snackbar.LENGTH_SHORT)
                    .setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_FADE)
                    .setAction(getString(R.string.snackbarButton)){}.show()
            }
        }
    }


}