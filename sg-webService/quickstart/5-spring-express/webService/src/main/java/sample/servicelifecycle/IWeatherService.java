package sample.servicelifecycle;

import sample.servicelifecycle.bean.Weather;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 11-10-15
 * Time: 下午6:41
 * To change this template use File | Settings | File Templates.
 */
public interface IWeatherService {

    Weather getWeather();

    String update(String forecast);

}
