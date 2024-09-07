package com.example.meowieskotlin.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class SignUpViewModel : ViewModel() {
    private var _email = mutableStateOf("")
    var email: MutableState<String>
        get() = _email
        set(value) { _email = value }

    private var _name = mutableStateOf("")
    var name: MutableState<String>
        get() = _name
        set(value) { _name = value }

    private var _password = mutableStateOf("")
    var password: MutableState<String>
        get() = _password
        set(value) { _password = value }

    private var _passwordRepeated = mutableStateOf("")
    var passwordRepeated: MutableState<String>
        get() = _passwordRepeated
        set(value) { _passwordRepeated = value }

    private var _checked = mutableStateOf(false)
    var checked: MutableState<Boolean>
        get() = _checked
        set(value) { _checked = value }

}