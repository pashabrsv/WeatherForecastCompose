package ru.apps.weatherforecastcompose.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.LocalContentColor
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import ru.apps.weatherforecastcompose.Data.WeatherModel
import ru.apps.weatherforecastcompose.ui.theme.SeaColor

@Composable
fun ListItem(item: WeatherModel){
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(3.dp),
        backgroundColor = SeaColor.copy(alpha = 0.4f),
        elevation = 0.dp,
        shape = RoundedCornerShape(5.dp)
        ){
        Row(modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically){
            Column(modifier = Modifier.padding(
                start = 8.dp,
                top = 3.dp,
                bottom = 3.dp)){
                Text(text = item.time, color = Color.White)
                Text(modifier = Modifier.padding(top = 3.dp),
                    text = item.condition, color = Color.White)
            }
            Text(text = item.currentTemp.ifEmpty { "${item.minTemp}/${item.maxTemp}" },
                color = Color.White,
                style = TextStyle(fontSize = 25.sp)
            )
            AsyncImage(model = "https:${item.icon}",
                contentDescription = "im6",
                modifier = Modifier.padding(top = 8.dp, bottom = 8.dp).
                size(35.dp))
        }
    }
}