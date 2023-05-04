package com.example.apianime

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.apianime.models.Anime
import com.example.apianime.navigation.NavHost
import com.example.apianime.screens.AnimeTop
import org.json.JSONObject


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NavHost()
        }
    }
}
