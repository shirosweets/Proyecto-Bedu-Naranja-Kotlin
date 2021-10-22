package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class RegisterFragment : Fragment() {
    private lateinit var registerButton: MaterialButton
    private lateinit var regFormUser: TextInputLayout
    private lateinit var regFormEmail: TextInputLayout
    private lateinit var regFormPhone: TextInputLayout
    private lateinit var regFormPassword: TextInputLayout
    private lateinit var nameInputText: TextInputEditText
    private lateinit var emailInputText: TextInputEditText
    private lateinit var phoneInputText: TextInputEditText
    private lateinit var passwordInputText: TextInputEditText
    private lateinit var inputMap: Map<TextInputEditText, TextInputLayout>

    private val isFormValid: () -> Boolean = {
        !(regFormUser.editText?.text.isNullOrEmpty() ||
                regFormEmail.editText?.text.isNullOrEmpty() ||
                regFormPhone.editText?.text.isNullOrEmpty() ||
                regFormPassword.editText?.text.isNullOrEmpty() ||
                regFormPassword.editText?.text.toString().length < 8)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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

        inputMap = mapOf(
            nameInputText to regFormUser,
            emailInputText to regFormEmail,
            phoneInputText to regFormPhone,
            passwordInputText to regFormPassword
        )

        for ((inputEditText, _) in inputMap) {
            inputEditText.doOnTextChanged { text, _, _, _ ->
                if (!text.isNullOrEmpty()) {
                    inputEditText.error = null
                }
            }
        }
        setClickListeners(view)
    }

    private fun setClickListeners(view: View) {
        registerButton.setOnClickListener {
            if(isFormValid()) {
                ConfigManager.prefs(requireActivity()).edit()
                    .putString("USER_EMAIL", emailInputText.text.toString())
                    .putString("USER_PASSWORD", passwordInputText.text.toString())
                    .apply()
                val action = RegisterFragmentDirections
                    .actionRegisterFragment2ToLoginFragment2()
                Navigation.findNavController(view).navigate(action)
            } else {
                if (passwordInputText.text.toString().length < 8) {
                    regFormPassword.error = getString(
                        R.string.notice_password_characters_less_than_8
                    )
                }
                for ((inputEditText, inputEditLayout) in inputMap) {
                    if (inputEditText.text.isNullOrEmpty()) {
                        inputEditLayout.error = getString(R.string.notice_incomplete_field)
                    }
                }

                Snackbar.make(
                    view,
                    getString(R.string.notice_in_complete_fields),
                    Snackbar.LENGTH_SHORT
                )
                    .setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_FADE)
                    .setAction(getString(R.string.snack_bar_button)) {}.show()
            }
        }
    }
}
