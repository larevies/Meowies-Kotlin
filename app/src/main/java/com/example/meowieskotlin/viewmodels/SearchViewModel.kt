package com.example.meowieskotlin.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.meowieskotlin.modules.Item

class SearchViewModel : ViewModel() {

    private val _searchRequest = mutableStateOf("")
    var searchRequest: String
        get() = _searchRequest.value
        set(value) { _searchRequest.value = value }

    private val _isSearchVisible = mutableStateOf(false)
    var isSearchVisible: Boolean
        get() = _isSearchVisible.value
        set(value) { _isSearchVisible.value = value }

    private var _searchResults = mutableListOf<Item>()
    var searchResults: MutableList<Item>
        get() = _searchResults
        set(value) { _searchResults = value }
    fun addToResults(value: Item) {
        _searchResults.add(value)
    }
}