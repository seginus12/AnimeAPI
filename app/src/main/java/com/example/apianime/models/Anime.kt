package com.example.apianime.models

data class Anime(
    val rank: Int,
    val title: String,
    val title_english: String,
    val title_japanese: String,
    val type: String,
    val score: Float,
    val status: String,
    val episodes: Int,
    val duration: String,
    var img_url: String,
    val genres: List<String>
)
