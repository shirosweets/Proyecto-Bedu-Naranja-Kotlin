package com.example.myapplication

object LoginCheck {

    fun checkEmailAndPassword(email:String,password:String):String{
        if(password.isNotEmpty() && email.isNotEmpty()){
            return if(password.length < 8){
                "PASSWORD_CHARACTERS_IS_LESS_THAN_8"
            }else{
                "SUCCESSFUL_LOGIN"
            }

        }else{
            return if(password.isEmpty() && email.isNotEmpty()){
                "PASSWORD_IS_EMPTY"
            }else if(password.isNotEmpty() && email.isEmpty()){
                "EMAIL_IS_EMPTY"
            }else{
                "EMPTY_FIELDS"
            }

        }
    }

}