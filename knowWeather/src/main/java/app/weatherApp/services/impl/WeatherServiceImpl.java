package app.weatherApp.services.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.alicp.jetcache.anno.Cached;

import app.weatherApp.dto.WeatherAverageBean;
import app.weatherApp.dto.WeatherMapBean;
import app.weatherApp.dto.WeatherMapTimeBean;
import app.weatherApp.services.WeatherService;
import springfox.documentation.spring.web.json.Json;


@Service
public class WeatherServiceImpl implements WeatherService {
    private final String URI = "http://api.openweathermap.org/data/2.5/forecast";
    private final String API_ID = "d2d790dde8d9d1da1398ca6eb2348a9a";

    private final RestTemplate restTemplate;

    public WeatherServiceImpl(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @Cached(expire = 10, timeUnit = TimeUnit.MINUTES)
    public ResponseEntity<?> weatherForecastAverage(String city) {
        List<WeatherAverageBean> result = new ArrayList<WeatherAverageBean>();
        try {
            WeatherMapBean weatherMap = this.restTemplate.getForObject(this.url(city), WeatherMapBean.class);

            for (LocalDate reference = LocalDate.now();
                 reference.isBefore(LocalDate.now().plusDays(4));
                 reference = reference.plusDays(1)) {
                final LocalDate ref = reference;
                List<WeatherMapTimeBean> collect = weatherMap.getList().stream()
                        .filter(x -> x.getDt().toLocalDate().equals(ref)).collect(Collectors.toList());
                if (!CollectionUtils.isEmpty(collect)) {
                    result.add(this.average(collect));
                }

            }
        } catch (HttpClientErrorException e) {
            return new ResponseEntity<>(new Json(e.getResponseBodyAsString()), e.getStatusCode());
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    private WeatherAverageBean average(List<WeatherMapTimeBean> list) {
        WeatherAverageBean result = new WeatherAverageBean();

        for (WeatherMapTimeBean item : list) {
            result.setDate(item.getDt().toLocalDate());
            result.plusMap(item);
        }

        result.totalize();

        return result;
    }

    private String url(String city) {
        return String.format(URI.concat("?q=%s").concat("&appid=%s").concat("&units=metric"), city, API_ID);
    }
}
