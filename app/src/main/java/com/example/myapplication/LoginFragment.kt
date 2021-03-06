package com.example.myapplication

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LoginFragment : Fragment() {
    private lateinit var loginFormUser: TextInputLayout
    private lateinit var loginFormPassword: TextInputLayout
    private lateinit var loginButton: MaterialButton
    private lateinit var registerRedirectBtn: Button
    private lateinit var userInputText: TextInputEditText
    private lateinit var passwordInputText: TextInputEditText
    private lateinit var loginProgressBar: ProgressBar
    private lateinit var sharedPreferences: SharedPreferences
    private val baseLoginUrl = "https://reqres.in/"
    private val baseUserUrl = "https://reqres.in/api/users/"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loginFormUser = view.findViewById(R.id.loginFormEmail)
        loginFormPassword = view.findViewById(R.id.loginFormPassword)
        loginButton = view.findViewById(R.id.buttonLogIn)
        registerRedirectBtn = view.findViewById(R.id.buttonCheckIn)
        userInputText = view.findViewById(R.id.userInputText)
        passwordInputText = view.findViewById(R.id.passwordInputText)
        loginProgressBar = view.findViewById(R.id.loginProgressBar)
        sharedPreferences = requireActivity().getSharedPreferences(
            getString(R.string.login_shared_preference_file),
            Context.MODE_PRIVATE
        )
        userInputText.setText(sharedPreferences.getString("USER_EMAIL", ""))

        setSharedPreferencesInputText()
        setTextChangeActions()
        setClickListeners(view)
    }

    private fun setSharedPreferencesInputText() {
        userInputText.setText(sharedPreferences.getString("USER_EMAIL", ""))
    }

    private fun setTextChangeActions() {
        userInputText.doOnTextChanged { text, _, _, _ ->
            if (!text.isNullOrEmpty()) {
                loginFormUser.error = null
            }
        }

        passwordInputText.doOnTextChanged { text, _, _, _ ->
            if (!text.isNullOrEmpty()) {
                loginFormPassword.error = null
            }
        }
    }

    private fun setClickListeners(view: View) {
        loginButton.setOnClickListener {
            if (isLoginFormValid()) {
                loginProgressBar.visibility = View.VISIBLE
                loginButton.isEnabled = false
                checkUser(view)
            } else {
                loginFormPassword.error = LoginManager.getPasswordErrorHint(
                    requireContext(),
                    passwordInputText.text?.toString()
                )
                if (userInputText.text.isNullOrEmpty()) {
                    loginFormUser.error = getString(R.string.notice_incomplete_field)
                }

                val incompleteFieldNotice = getString(R.string.notice_in_complete_fields)
                Snackbar.make(view, incompleteFieldNotice, Snackbar.LENGTH_SHORT)
                    .setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_FADE)
                    .setAction(getString(R.string.snack_bar_button)) {}.show()
            }
        }

        registerRedirectBtn.setOnClickListener {
            findNavController().navigate(
                R.id.action_loginFragment2_to_registerFragment2,
                null
            )
        }
    }

    private fun getRetrofit(baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun checkUser(view: View) {
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit(baseLoginUrl)
                .create(APIService::class.java)
                .loginUser(
                    userInputText.text.toString(),
                    passwordInputText.text.toString()
                )

            activity?.runOnUiThread {
                if (call.isSuccessful) {
                    getUserData(userInputText.text.toString())
                    loginSuccessfully()
                } else {
                    loginProgressBar.visibility = View.INVISIBLE
                    loginButton.isEnabled = true

                    Snackbar.make(
                        view,
                        getString(R.string.notice_user_not_found),
                        Snackbar.LENGTH_SHORT
                    )
                        .setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_FADE)
                        .setAction(getString(R.string.snack_bar_button)) {}.show()
                }
            }
        }
    }

    private fun loginSuccessfully() {
        loginProgressBar.visibility = View.INVISIBLE
        loginButton.isEnabled = true
        findNavController().navigate(
            R.id.action_loginFragment2_to_menuActivity,
            null
        )
    }

    private fun isLoginFormValid(): Boolean {
        val userValid: Boolean = !userInputText.text.isNullOrEmpty()
        val passValid: Boolean = LoginManager.isPasswordValid(passwordInputText.text?.toString())
        return (userValid && passValid)
    }

    private fun getUserData(userEmail: String) {
        for (userNumber in 1..12) {
            CoroutineScope(Dispatchers.IO).launch {
                val call = getRetrofit(baseUserUrl)
                    .create(APIService::class.java)
                    .getUserData(userNumber.toString())
                val userReceived = call.body()

                activity?.runOnUiThread {
                    if (call.isSuccessful) {
                        if (userReceived?.data?.email == userEmail) {
                            savedSharedPreferencesUser(userReceived)
                        }
                    } else {
                        Log.d("Error:", "User data call failed")
                    }
                }
            }
        }
    }

    private fun savedSharedPreferencesUser(user: User) {
        sharedPreferences.edit()
            .putString("USER_EMAIL", user.data.email)
            .putString("USER_AVATAR", user.data.avatar)
            .putString("USER_FIRST_NAME", user.data.first_name)
            .apply()
        LoginManager.logIn(requireActivity())
    }
}
