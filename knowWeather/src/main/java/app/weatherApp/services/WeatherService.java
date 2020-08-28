package app.weatherApp.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
public interface WeatherService {

    public ResponseEntity<?> weatherForecastAverage(String city);
}
