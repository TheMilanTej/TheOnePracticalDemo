package com.milantejani.theonepractical.ui.weather

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.milantejani.theonepractical.AppConstant
import com.milantejani.theonepractical.R
import com.milantejani.theonepractical.data.model.ApiResp
import com.milantejani.theonepractical.databinding.ActivityWeatherBinding
import com.milantejani.theonepractical.ui.welcome.MainActivity
import com.milantejani.theonepractical.utils.PreferenceHelper
import com.milantejani.theonepractical.utils.PreferenceHelper.set
import com.milantejani.theonepractical.utils.viewBinding

class WeatherActivity : AppCompatActivity() {

    private lateinit var pref: SharedPreferences
    private val binding by viewBinding(ActivityWeatherBinding::inflate)

    private val viewModel: WeatherViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        pref = PreferenceHelper.customPrefs(this@WeatherActivity, AppConstant.USER_LOGIN)


        with(binding.incActionBar) {
            btnLogout.visibility = View.VISIBLE
            txtMenuAdd.visibility = View.INVISIBLE
            txtMenuTitle.text = getString(R.string.weather)

            btnLogout.setOnClickListener {
                pref.set(AppConstant.USER_LOGGED_IN, false)
                startActivity(Intent(this@WeatherActivity, MainActivity::class.java))
                finish()
            }
            txtMenuBack.setOnClickListener {
                onBackPressed()
            }
        }

        viewModel.weatherData.observe(this) { apiResp ->
            apiResp?.let {
                when (it) {
                    is ApiResp.Loading -> {
                        binding.txtWeatherDataMix.text = "Loading..."
                    }

                    is ApiResp.Success -> {
                        val weatherStr = it.data.current?.let { currentWeatherData ->
                            "Temp:- ${currentWeatherData.temp} \n" + "Weather:- ${
                                currentWeatherData.weather?.get(0)?.main
                            } \n" + "Weather:- ${currentWeatherData.weather?.get(0)?.description}\n" + "humidity:- ${currentWeatherData.humidity}\n" + "Wind Speed:- ${currentWeatherData.windSpeed}"
                        }
                        binding.txtWeatherDataMix.text = weatherStr
                    }

                    is ApiResp.Error -> {
                        binding.txtWeatherDataMix.text = it.message
                    }
                }
            }
        }
    }

    override fun onBackPressed() {
        //super.onBackPressed()
        finish()
    }
}