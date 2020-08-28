package app.weatherApp.controller;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import app.weatherApp.dto.WeatherAverageBean;
import app.weatherApp.services.WeatherService;
import static org.mockito.Mockito.when;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WeatherControllerTest {

	@InjectMocks
	private WeatherController weatherController;
	
	@Mock
	private WeatherService weatherService;
	
	@Before
	public void setup() throws Exception {
		
	}
	
	@Test
	public void testWeatherForecastAverage() {
		List<WeatherAverageBean> result = new ArrayList<WeatherAverageBean>();
		ResponseEntity responseEntity = new ResponseEntity<>(result, HttpStatus.OK);
		when(weatherService.weatherForecastAverage(Mockito.anyString())).thenReturn(responseEntity);
		
		ResponseEntity responseEntityFromCall = weatherController.weatherForecastAverage("TEST_CITY");
		assertEquals(responseEntityFromCall.getStatusCode(), HttpStatus.OK);
		
		List<WeatherAverageBean> resultFromCall = (List<WeatherAverageBean>) responseEntityFromCall.getBody();
		assertNotNull(resultFromCall);
	}
	
}
