package com.example.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.widget.doOnTextChanged
import androidx.navigation.fragment.findNavController
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class LoginFragment : Fragment() {
    private lateinit var loginFormUser : TextInputLayout
    private lateinit var loginFormPassword : TextInputLayout
    private lateinit var loginButton : MaterialButton
    private lateinit var registerRedirectBtn : Button
    private lateinit var userInputText : TextInputEditText
    private lateinit var passwordInputText : TextInputEditText

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loginFormUser = view.findViewById(R.id.loginFormEmail)
        loginFormPassword= view.findViewById(R.id.loginFormPassword)
        loginButton= view.findViewById(R.id.buttonLogIn)
        registerRedirectBtn= view.findViewById(R.id.buttonCheckIn)
        userInputText = view.findViewById(R.id.userInputText)
        passwordInputText = view.findViewById(R.id.passwordInputText)

        setTextChangeActions()
        setClickListeners(view)

    }


    private fun setTextChangeActions(){
        userInputText.doOnTextChanged { text,_,_,_ ->
            if(text!!.isNotEmpty()){
                loginFormUser.error = null
            }
        }

        passwordInputText.doOnTextChanged { text,_,_,_ ->
            if(text!!.isNotEmpty()){
                loginFormPassword.error = null
            }
        }
    }

    private fun setClickListeners(view: View){
        loginButton.setOnClickListener{
            val emailNotEmpty: Boolean = !loginFormUser.editText?.text.isNullOrEmpty()
            val passNotEmpty: Boolean = !loginFormPassword.editText?.text.isNullOrEmpty()

            if(emailNotEmpty && passNotEmpty){
                findNavController().navigate(
                    R.id.action_loginFragment2_to_menuActivity,
                    null)
            }
            else{
                if(!emailNotEmpty) { loginFormUser.error = getString(R.string.noticeIncompleteField)}
                if(!passNotEmpty) { loginFormPassword.error = getString(R.string.noticeIncompleteField)}

                Snackbar.make(view, getString(R.string.noticeIncompleteFields), Snackbar.LENGTH_SHORT)
                    .setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_FADE)
                    .setAction(getString(R.string.snackbarButton)){}.show()
                }
        }

        registerRedirectBtn.setOnClickListener{
            findNavController().navigate(
                R.id.action_loginFragment2_to_registerFragment2,
                null
            )
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