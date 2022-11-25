package ru.apps.weatherforecastcompose.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import ru.apps.weatherforecastcompose.R
import ru.apps.weatherforecastcompose.navigation.Screens
import ru.apps.weatherforecastcompose.ui.theme.WeatherForecastComposeTheme

@Composable
fun SplashScreen(navController: NavController){
    var startAnimate by remember {
        mutableStateOf(false)
    }
    val alphaAnimation = animateFloatAsState(
        targetValue = if (startAnimate) 1f else 0f,
        animationSpec = tween(durationMillis = 3000)
    )
    LaunchedEffect(key1 = true){
        startAnimate = true
        delay(4000)
        navController.navigate(Screens.Main.route)
    }
    Splash(alpha = alphaAnimation.value)
}

@Composable
fun Splash(alpha: Float){
    Box(modifier = Modifier.padding(top = 80.dp, start = 10.dp)
    ){
        Icon(
            modifier = Modifier.padding(top = 16.dp, start = 20.dp)
                .size(90.dp)
                .alpha(alpha = alpha),
            painter = painterResource(id = R.drawable.created_author),
            contentDescription = "ic1",
            tint = Color.Unspecified  //херовина, чтобы иконка не была чёрной
        )
    }
}

@Composable
@Preview(showBackground = true)
fun prevSplash(){
    WeatherForecastComposeTheme() {
        Splash(1f)
    }
}