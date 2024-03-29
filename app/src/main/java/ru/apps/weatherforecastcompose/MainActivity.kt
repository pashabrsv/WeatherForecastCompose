package ru.apps.weatherforecastcompose

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import ru.apps.weatherforecastcompose.Data.WeatherModel
import ru.apps.weatherforecastcompose.navigation.SetupNavHost
import ru.apps.weatherforecastcompose.screens.MainCard
import ru.apps.weatherforecastcompose.screens.SearchCity
import ru.apps.weatherforecastcompose.screens.TabLayout
import ru.apps.weatherforecastcompose.ui.theme.WeatherForecastComposeTheme

const val API_KEY = "c77d8f0d8fc24406a19122925221909"
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            WeatherForecastComposeTheme {
                val navController = rememberNavController()
                SetupNavHost(navController = navController)
                val daysList = remember{
                    mutableStateOf(listOf<WeatherModel>())
                }
                val dialogState = remember{
                    mutableStateOf(false)
                }

                 val currentDay = remember{
                     mutableStateOf(WeatherModel(
                         "",
                         "",
                         "",
                         "0.0",
                         "0.0",
                         "0.0",
                         "",
                         ""
                     ))
                 }
                if (dialogState.value){
                    SearchCity(dialogState, onSubmit = {
                        getData(it, this, daysList, currentDay)
                    })
                }
                getData("Krasnodar", this, daysList, currentDay)
                Image(
                    painter = painterResource(id = R.drawable.sky),
                    contentDescription = "im1",
                    modifier = Modifier
                        .fillMaxSize()
                        .alpha(0.7f),
                    contentScale = ContentScale.Crop
                )
                Column {
                    MainCard(currentDay, onClickUpdate = {
                        getData("Krasnodar", this@MainActivity, daysList, currentDay)
                    },
                    onClickSearchSity = {
                        dialogState.value = true
                    })
                    TabLayout(daysList, currentDay)
                }

            }

        }
    }
}

private fun getData(city: String, context: Context,  // здесь прописать метод замены города по нажатию на поиск
                    daysList: MutableState<List<WeatherModel>>,
                    currentDay: MutableState<WeatherModel>){
    val url = "https://api.weatherapi.com/v1/forecast.json?key=$API_KEY" +
            "&q=$city" +
            "&days=" +
            "3" +
            "&aqi=no&alerts=no" +
            "&lang=ru"

    val queue = Volley.newRequestQueue(context)
    val sRequest = StringRequest(
        Request.Method.GET,
            url,
        {
//            В функции getData добавляем в ссылку запроса параметр: &lang=ru,
//            потом изменяем кодировку переменной response: val resp = String(response.toByteArray(Charsets.ISO_8859_1),
//            Charsets.UTF_8) и передаем переменную resp в: val list = getWeatherByDay(resp)
            response ->
            val resp = String(response.toByteArray(Charsets.ISO_8859_1), Charsets.UTF_8)
            val list = getWeatherByDays(resp)
            currentDay.value = list[0]
            daysList.value = list

        },
        {
            Log.d("MyLog", "VolleyError: $it")
        }
    )
    queue.add(sRequest)
}

private fun getWeatherByDays(response: String):List<WeatherModel>{
    if(response.isEmpty()) return listOf()
    val list = ArrayList<WeatherModel>()
    val mainObject = JSONObject(response)
    val city = mainObject.getJSONObject("location").getString("name")
    val days = mainObject.getJSONObject("forecast").getJSONArray("forecastday")

    for(i in 0 until days.length()){   //Попробуй здесь указать единицу, чтобы отображать список со второго дня
        val item = days[i] as JSONObject
        list.add(
            WeatherModel(
                city,
                item.getString("date"),
                item.getJSONObject("day").getJSONObject("condition").getString("text"),
                "",
                item.getJSONObject("day").getString("mintemp_c"),
                item.getJSONObject("day").getString("maxtemp_c"),
                item.getJSONObject("day").getJSONObject("condition")
                    .getString("icon"),
                item.getJSONArray("hour").toString()
            )
        )
    }
    list[0] = list[0].copy(
        time = mainObject.getJSONObject("current").getString("last_updated"),
        currentTemp = mainObject.getJSONObject("current").getString("temp_c"),
    )
    return list
}






