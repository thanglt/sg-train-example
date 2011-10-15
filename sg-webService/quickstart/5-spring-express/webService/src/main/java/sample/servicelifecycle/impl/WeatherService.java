package sample.servicelifecycle.impl;

import sample.servicelifecycle.IWeatherService;
import sample.servicelifecycle.bean.Weather;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 11-10-15
 * Time: 下午6:42
 * To change this template use File | Settings | File Templates.
 */
//@Componet
public class WeatherService implements IWeatherService {

    private Weather weather;

    public void setWeather(Weather weather) {
        this.weather = weather;
    }

    public Weather getWeather() {
        return  weather;
    }

    public String update(String forecast) {
        this.weather.setForecast(forecast);
        return "operation success, weather is : " + weather.toString();
    }
}
