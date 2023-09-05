package eliqweather.data.mapper

import eliqweather.domain.models.Daily
import eliqweather.domain.models.DailyWeatherModel
import eliqweather.domain.models.Hourly
import eliqweather.domain.models.HourlyWearherModel
import eliqweather.domain.models.WeatherInfoModel
import eliqweather.domain.models.WeatherResponse
import eliqweather.data.utils.orZero

/**
 * @Author: Shahab Azimi
 * @Date: 2023 - 09 - 02
 **/

fun WeatherResponse.toDomain() = WeatherInfoModel.Response(
    latitude = latitude,
    longitude = longitude,
    timezone = timezone,
    elevation = elevation,
    hourlyWeather = hourly.toDomain(),
    dailyWeather = daily.toDomain()
)

fun Hourly.toDomain(): List<HourlyWearherModel> {
    val response = mutableListOf<HourlyWearherModel>()
    time.forEachIndexed { index, data ->
        response.add(
            HourlyWearherModel(
                time = data,
                precipitation = precipitation_probability[index].orZero(),
                temperature = temperature_2m[index].orZero()
            )
        )
    }
    return response
}

fun Daily.toDomain(): List<DailyWeatherModel> {
    val response = mutableListOf<DailyWeatherModel>()
    time.forEachIndexed { index, data ->
        response.add(
            DailyWeatherModel(
                date = data,
                maxTemperature = temperature_2m_max[index].orZero(),
                minTemperature = temperature_2m_min[index].orZero(),
                weatherCode = getWeatherCondition(weathercode[index].orZero()).first,
                weatherIcon = getWeatherCondition(weathercode[index].orZero()).second
            )
        )
    }

    return response
}