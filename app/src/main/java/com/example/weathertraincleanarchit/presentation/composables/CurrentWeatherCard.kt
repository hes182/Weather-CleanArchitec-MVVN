package com.example.weathertraincleanarchit.presentation.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weathertraincleanarchit.presentation.state.CurrentWeatherState
import com.example.weathertraincleanarchit.presentation.state.DailyWeatherState
import com.example.weathertraincleanarchit.presentation.viewmodels.CurrentWeatherViewModel

@Composable
fun CurrentWeatherCard(
    modifier: Modifier,
    state: CurrentWeatherState,
    state1: DailyWeatherState,
    currentWatherVM: CurrentWeatherViewModel
) {
    state.data.let {
        Card(
            modifier = modifier
                .padding(14.dp)
                .fillMaxWidth()
                .height(450.dp),
            shape = RoundedCornerShape(20.dp),
            elevation = CardDefaults.outlinedCardElevation(10.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = currentWatherVM.city.value,
                    fontSize = 40.sp,
                    fontStyle = FontStyle.Normal,
                    fontWeight = FontWeight.ExtraBold,
                    modifier = modifier.padding(start = 13.dp),
                    softWrap = true,
                )
                Spacer(modifier = modifier.height(3.dp))
                Text(text = "${it?.weatherType?.weatherDesc}",
                    fontStyle = FontStyle.Normal,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Light,
                    modifier = modifier.padding(start = 13.dp)
                    )
                Spacer(modifier = modifier.height(0.5.dp))
                Text(
                    text = "${it?.windSpeed} m/s",
                    fontWeight = FontWeight.Light,
                    fontStyle = FontStyle.Normal,
                    fontSize = 13.sp,
                    modifier = modifier.padding(start = 13.dp)
                )
                Divider(
                    modifier = modifier
                        .padding(20.dp)
                        .fillMaxWidth()
                        .height(2.dp),
                    color = Color.LightGray, thickness = 0.3.dp
                )
                DailyWeatherComp(state = state1)
            }
        }
    }
}