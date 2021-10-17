package com.example.myapplication

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.getColor
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.widget.doOnTextChanged
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
    private var sharedPreferences: SharedPreferences? = null
    private val BASE_LOGIN_URL = "https://reqres.in/"
    private val BASE_USER_URL = "https://reqres.in/api/users/"

    private lateinit var notificationBuyButton: Button

    val CHANNEL_OTHERS = "OTROS"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            setNotificationChannel()
        }
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        super.onViewCreated(view, savedInstanceState)
        loginFormUser = view.findViewById(R.id.loginFormEmail)
        loginFormPassword = view.findViewById(R.id.loginFormPassword)
        loginButton = view.findViewById(R.id.buttonLogIn)
        registerRedirectBtn = view.findViewById(R.id.buttonCheckIn)
        userInputText = view.findViewById(R.id.userInputText)
        passwordInputText = view.findViewById(R.id.passwordInputText)
        loginProgressBar = view.findViewById(R.id.loginProgressBar)

        notificationBuyButton = view.findViewById(R.id.notificationBuy)

        sharedPreferences = this.activity?.getSharedPreferences("org.bedu.sharedpreferences", Context.MODE_PRIVATE)

        setSharedPreferencesInputText()
        setTextChangeActions()
        setClickListeners(view)
        notificationBuyButton.setOnClickListener{
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                BuyNotification()
            }
        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setNotificationChannel(){
        val name = getString(R.string.notification_1)
        val descriptionText = getString(R.string.notify_body_1)
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(CHANNEL_OTHERS, name, importance).apply{
            description = descriptionText
        }

        val notificationManager: NotificationManager =
            requireContext().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.createNotificationChannel(channel)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun BuyNotification(){
        var builder = NotificationCompat.Builder(requireContext(), CHANNEL_OTHERS)
            .setSmallIcon(R.drawable.ic_bedu_shop)
            .setColor(getColor(requireContext(), R.color.secondaryColor))
            .setContentTitle(getString(R.string.notification_1))
            .setContentText(getString(R.string.notify_body_1))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        // Send notification
        with(NotificationManagerCompat.from(requireContext())){
            notify(20, builder.build()) // id generic
        }

    }

    private fun setSharedPreferencesInputText() {
        userInputText.setText(sharedPreferences?.getString("USER_EMAIL", ""))
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
                checkUser(view)
//                Thread{
//                    checkUserEmail(view)
//                }.start()
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

    private fun getRetrofit(baseUrl:String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()


    }

    private fun checkUser(view: View){
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit(BASE_LOGIN_URL).create(APIService::class.java).loginUser(userInputText.text.toString(),passwordInputText.text.toString())
            activity?.runOnUiThread{
                if(call.isSuccessful){
                    getUserData(userInputText.text.toString())
                    loginSuccessfully()
                }else{
                    loginProgressBar.visibility = View.INVISIBLE
                    loginButton.isEnabled = true

                    Snackbar.make(
                        view,
                        getString(R.string.noticeUserNotFound),
                        Snackbar.LENGTH_SHORT
                    )
                        .setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_FADE)
                        .setAction(getString(R.string.snackbarButton)) {}.show()}
                }

            }
        }

    private fun loginSuccessfully(){
        loginProgressBar.visibility = View.INVISIBLE
        loginButton.isEnabled = true
        findNavController().navigate(
            R.id.action_loginFragment2_to_menuActivity,
            null
        )
    }

    private fun getUserData(userEmail:String){

        for(userNumber in 1..12) {
            CoroutineScope(Dispatchers.IO).launch {
                val call = getRetrofit(BASE_USER_URL).create(APIService::class.java).getUserData(userNumber.toString())
                val userReceived = call.body()
                activity?.runOnUiThread{
                    if(call.isSuccessful){
                        if(userReceived?.data?.email == userEmail){
                            savedSharedPreferencesUser(userReceived)
                        }
                    }else{
                        Log.d("Error:","User data call failed")
                    }
                }
            }
        }

    }

    private fun savedSharedPreferencesUser(user : User){
        sharedPreferences?.edit()
            ?.putBoolean("USER_ACCESS",true)
            ?.putString("USER_EMAIL", user.data.email)
            ?.putString("USER_AVATAR", user.data.avatar)
            ?.putString("USER_FIRST_NAME", user.data.first_name)
            ?.apply()
    }
}