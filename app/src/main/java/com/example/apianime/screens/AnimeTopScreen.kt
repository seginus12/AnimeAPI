package com.example.apianime.screens

import android.content.Context
import android.util.Log
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.apianime.models.Anime
import com.example.apianime.models.Shared
import com.example.apianime.navigation.NavRoute
import org.json.JSONObject

@Composable
fun AnimeTop(navController: NavController, shared: Shared) {
    //Log.d("from animetop", animeList.value[18].img_url)
    val animeList = remember { mutableStateOf(arrayListOf<Anime>()) }
    getData(LocalContext.current, animeList)
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(ScrollState(0))
            .padding(start = 2.dp, end = 2.dp, bottom = 2.dp)
    ) {
        for (i in 0 until animeList.value.size - animeList.value.size % 2 step 2) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Box(
                    contentAlignment = Alignment.BottomCenter,
                    modifier = Modifier
                        .height(280.dp)
                        .padding(2.dp)
                        .background(Color.Black)
                        .padding(1.dp)
                        .weight(1f)
                        .clickable(
                            onClick = {
                                shared.addAnime(animeList.value[i])
                                navController.navigate(NavRoute.AnimeDetailScreen.route)
                            })
                ) {
                    AsyncImage(
                        model = animeList.value[i].img_url,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()

                    )
                    Box(
                        contentAlignment = Alignment.BottomCenter,
                        modifier = Modifier
                            .background(Color(61, 61, 61, 154))
                            .fillMaxWidth()
                            .padding(2.dp)
                    ){
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ){
                            Text(
                                text = animeList.value[i].title_english,
                                color = Color.White,
                                fontSize = 16.sp,
                                textAlign = TextAlign.Center
                            )
                            Text(
                                text = "#${animeList.value[i].rank}",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp
                            )
                        }

                    }

                }
                Box(
                    contentAlignment = Alignment.BottomCenter,
                    modifier = Modifier
                        .height(280.dp)
                        .padding(2.dp)
                        .background(Color.Black)
                        .padding(1.dp)
                        .weight(1f)
                        .clickable(
                            onClick = {
                                shared.addAnime(animeList.value[i + 1])
                                navController.navigate(NavRoute.AnimeDetailScreen.route)
                            })
                ) {
                    AsyncImage(
                        model = animeList.value[i + 1].img_url,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                    Box(
                        contentAlignment = Alignment.BottomCenter,
                        modifier = Modifier
                            .background(Color(61, 61, 61, 154))
                            .fillMaxWidth()
                            .padding(2.dp)
                    ){
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ){
                            Text(
                                text = animeList.value[i + 1].title_english,
                                color = Color.White,
                                fontSize = 16.sp,
                                textAlign = TextAlign.Center
                            )
                            Text(
                                text = "#${animeList.value[i + 1].rank}",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp
                            )
                        }

                    }
                }
            }
        }
    }
}


fun getData(context: Context, animeList: MutableState<ArrayList<Anime>>){
    val url = "https://api.jikan.moe/v4/top/anime"
    val queue = Volley.newRequestQueue(context)
    var e: List<Anime>
    val sRequest: StringRequest = object : StringRequest(
        Method.GET,
        url,
        { response ->
            animeList.value = parseJSON(response)
        },
        {
            Log.d("MyLog", "VolleyError: $it")
        }
    ) {}
    queue.add(sRequest)

}

private fun parseJSON(response: String): ArrayList<Anime>{
    if (response.isEmpty()) return arrayListOf()
    val list = ArrayList<Anime>()
    val mainObject = JSONObject(response)
    val rawanimeList = mainObject.getJSONArray("data")
    for (i in 0 until rawanimeList.length()){
        val item = rawanimeList[i] as JSONObject
        val genresRawList = item.getJSONArray("genres")
        val genres = ArrayList<String>()
        for (i in 0 until genresRawList.length()) {
            val genresItem = genresRawList[i] as JSONObject
            genres.add(genresItem.getString("name"))
        }
        list.add(
            Anime(
                item.getString("rank").toInt(),
                item.getString("title"),
                item.getString("title_english"),
                item.getString("title_japanese"),
                item.getString("type"),
                item.getString("score").toFloat(),
                item.getString("status"),
                item.getString("episodes").toInt(),
                item.getString("duration"),
                item.getJSONObject("images").getJSONObject("jpg").getString("large_image_url"),
                genres = genres
            )
        )
    }
    return list
}

