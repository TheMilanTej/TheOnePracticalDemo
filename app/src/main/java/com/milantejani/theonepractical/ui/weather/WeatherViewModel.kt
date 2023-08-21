package com.milantejani.theonepractical.ui.weather

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.milantejani.theonepractical.AppConstant.WEATHER_API_KEY
import com.milantejani.theonepractical.data.RetrofitClient
import com.milantejani.theonepractical.data.model.ApiResp
import com.milantejani.theonepractical.data.model.WeatherData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WeatherViewModel : ViewModel() {
    private val _weatherData = MutableLiveData<ApiResp<WeatherData>>(ApiResp.Loading)
    val weatherData: LiveData<ApiResp<WeatherData>> = _weatherData

    init {
        fetchWeatherData()
    }

    private fun fetchWeatherData() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val retrofitClient = RetrofitClient.getRetrofitClient()
                val response = retrofitClient.getWeatherData(
                    12.9082847623315, 77.65197822993314, "imperial", WEATHER_API_KEY
                )
                if (response.isSuccessful) {
                    response.body()?.let {
                        _weatherData.postValue(ApiResp.Success(it))
                    }
                } else {
                    _weatherData.postValue(ApiResp.Error(response.message()))
                }
            } catch (ex: Exception) {
                _weatherData.postValue(ApiResp.Error(ex.localizedMessage ?: "Something went wrong"))
            }
        }

    }
}