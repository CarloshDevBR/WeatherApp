package com.example.weatherapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.weatherapp.model.WeatherModel
import com.example.weatherapp.service.api.NetworkResponse
import com.example.weatherapp.viewmodel.WeatherViewModel

private const val defaultCity = "London"

@Composable
fun WeatherScreen(viewModel: WeatherViewModel) {
    var city by remember { mutableStateOf("") }

    val weather = viewModel.weather.observeAsState()

    LaunchedEffect(Unit) {
        viewModel.getData(defaultCity)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            OutlinedTextField(
                modifier = Modifier.weight(1f),
                value = city,
                label = {
                    Text("Search for any location")
                },
                onValueChange = { city = it }
            )

            IconButton(onClick = {
                if (city.isNotEmpty()) {
                    viewModel.getData(city)
                }
            }) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search for any location"
                )
            }
        }

        when (val result = weather.value) {
            is NetworkResponse.Failure -> {
                Text(text = result.message)
            }

            NetworkResponse.Loading -> {
                CircularProgressIndicator()
            }

            is NetworkResponse.Success -> {
                WeatherDetails(result.data)
            }

            else -> {}
        }
    }
}

@Composable
fun WeatherDetails(data: WeatherModel) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.Bottom
        ) {
            Icon(
                imageVector = Icons.Default.LocationOn,
                contentDescription = "Location icon",
                modifier = Modifier.size(40.dp)
            )

            Text(text = data.location.name, fontSize = 30.sp)

            Spacer(modifier = Modifier.width(8.dp))

            Text(text = data.location.country, fontSize = 18.sp, color = Color.Gray)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "${data.current.temp_c} ° c",
            fontSize = 56.sp,
            fontWeight = FontWeight.Bold,
        )

        Spacer(modifier = Modifier.height(16.dp))

        AsyncImage(
            model = "https:${data.current.condition.icon}".replace("64x64", "128x128"),
            contentDescription = "Condition icon",
            modifier = Modifier.size(160.dp)
        )

        Text(
            text = data.current.condition.text,
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(16.dp))

        Card {
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    WeatherKeyVal("Humidity", data.current.humidity.toString())

                    WeatherKeyVal("Wind Speed", "${data.current.wind_kph} km/h")
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    WeatherKeyVal("UV", data.current.uv.toString())

                    WeatherKeyVal("Participation", "${data.current.precip_mm} mm")
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    WeatherKeyVal("Local Time", data.location.localtime.split(" ")[1])

                    WeatherKeyVal("Local Date", data.location.localtime.split(" ")[0])
                }
            }
        }
    }
}

@Composable
fun WeatherKeyVal(key: String, value: String) {
    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = value, fontSize = 20.sp, fontWeight = FontWeight.Bold)

        Text(text = key, fontWeight = FontWeight.SemiBold, color = Color.Gray)
    }
}