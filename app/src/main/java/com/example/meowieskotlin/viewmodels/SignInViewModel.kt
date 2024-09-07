package com.example.meowieskotlin.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class SignInViewModel : ViewModel() {
    private var _email = mutableStateOf("")
    var email: MutableState<String>
        get() = _email
        set(value) { _email = value }

    private var _password = mutableStateOf("")
    var password: MutableState<String>
        get() = _password
        set(value) { _password = value }

}