package com.example.apianime.models

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class Shared: ViewModel() {
    var anime by mutableStateOf<Anime?>(null)
        private set

    fun addAnime(newAnime: Anime){
        anime = newAnime
    }
}