package com.example.meowieskotlin.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class ChangeViewModel : ViewModel() {

    private var _newEmail = mutableStateOf("")
    var newEmail: MutableState<String>
        get() = _newEmail
        set(value) { _newEmail = value }

    private var _newName = mutableStateOf("")
    var newName: MutableState<String>
        get() = _newName
        set(value) { _newName = value }

    private var _oldPassword = mutableStateOf("")
    var oldPassword: MutableState<String>
        get() = _oldPassword
        set(value) { _oldPassword = value }

    private var _newPassword = mutableStateOf("")
    var newPassword: MutableState<String>
        get() = _newPassword
        set(value) { _newPassword = value }

    private var _newPasswordConfirmed = mutableStateOf("")
    var newPasswordConfirmed: MutableState<String>
        get() = _newPasswordConfirmed
        set(value) { _newPasswordConfirmed = value }

}