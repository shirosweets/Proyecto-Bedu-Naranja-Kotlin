package com.example.myapplication

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import androidx.core.widget.doOnTextChanged
import androidx.navigation.fragment.findNavController
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import okhttp3.*
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

class LoginFragment : Fragment() {

    private lateinit var loginFormUser: TextInputLayout
    private lateinit var loginFormPassword: TextInputLayout
    private lateinit var loginButton: MaterialButton
    private lateinit var registerRedirectBtn: Button
    private lateinit var userInputText: TextInputEditText
    private lateinit var passwordInputText: TextInputEditText
    private lateinit var loginProgressBar: ProgressBar
    private var sharedPreferences: SharedPreferences? = null
    private val baseUrl = "https://reqres.in/api/users/"


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
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

        sharedPreferences =
            this.activity?.getSharedPreferences("org.bedu.sharedpreferences", Context.MODE_PRIVATE)

        setSharedInputText()
        setTextChangeActions()
        setClickListeners(view)

    }

    private fun setSharedInputText() {
        userInputText.setText(sharedPreferences?.getString("USER_EMAIL", ""))
        passwordInputText.setText(sharedPreferences?.getString("USER_PASSWORD", ""))
    }

    private fun setTextChangeActions() {
        userInputText.doOnTextChanged { text, _, _, _ ->
            if (text!!.isNotEmpty()) {
                loginFormUser.error = null
            }
        }

        passwordInputText.doOnTextChanged { text, _, _, _ ->
            if (text!!.isNotEmpty()) {
                loginFormPassword.error = null
            }
        }
    }

    private fun setClickListeners(view: View) {

        loginButton.setOnClickListener {
            val emailNotEmpty: Boolean = !loginFormUser.editText?.text.isNullOrEmpty()
            val passNotEmpty: Boolean = !loginFormPassword.editText?.text.isNullOrEmpty()

            if (emailNotEmpty && passNotEmpty){
                loginProgressBar.visibility = View.VISIBLE
                loginButton.isEnabled = false
                Thread{
                    checkUserEmail(view)
                }.start()
            }
            else {
                if (!emailNotEmpty) {
                    loginFormUser.error = getString(R.string.noticeIncompleteField)
                }
                if (!passNotEmpty) {
                    loginFormPassword.error = getString(R.string.noticeIncompleteField)
                }

                Snackbar.make(
                    view,
                    getString(R.string.noticeIncompleteFields),
                    Snackbar.LENGTH_SHORT
                )
                    .setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_FADE)
                    .setAction(getString(R.string.snackbarButton)) {}.show()
            }
        }

        registerRedirectBtn.setOnClickListener {
            findNavController().navigate(
                R.id.action_loginFragment2_to_registerFragment2,
                null
            )
        }
    }

    private fun checkUserEmail(view: View) {
        var check = false
        for(userNumber in 1..12){
            val okHttpClient = OkHttpClient()
            val url = "$baseUrl$userNumber"
            val request = Request.Builder()
                .url(url)
                .build()

            try {
                val response = okHttpClient.newCall(request).execute()
                val body = response.body?.string()

                val json = JSONObject(body)
                val dataJSON = JSONObject(json.getString("data"))
                val email = dataJSON.getString("email")
                if(userInputText.text.toString() == email){
                    activity?.runOnUiThread {
                        sharedPreferences?.edit()
                            ?.putString("USER_EMAIL", userInputText.text.toString())
                            ?.putString("USER_PASSWORD", passwordInputText.text.toString())
                            ?.putString("USER_IMAGE", dataJSON.getString("avatar"))
                            ?.putString("USER_FIRST_NAME", dataJSON.getString("first_name"))
                            ?.apply()

                        loginSuccessfully()
                    }
                    check = true
                    break
                }
            }catch (e: Exception) {
                e.printStackTrace()
            }
        }
        if(!check){
            activity?.runOnUiThread {
                loginProgressBar.visibility = View.INVISIBLE
                loginButton.isEnabled = true

            }

            Snackbar.make(
                view,
                getString(R.string.noticeUserNotFound),
                Snackbar.LENGTH_SHORT
            )
                .setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_FADE)
                .setAction(getString(R.string.snackbarButton)) {}.show()}
    }

    private fun loginSuccessfully(){
        loginProgressBar.visibility = View.INVISIBLE
        loginButton.isEnabled = true
        findNavController().navigate(
            R.id.action_loginFragment2_to_menuActivity,
            null
        )
    }


}