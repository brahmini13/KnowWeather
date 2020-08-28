package app.weatherApp.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class WeatherMapTimeBean {

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonProperty("dt_txt")
	private LocalDateTime dt;

	private WeatherMapTimeMainBean main;

	public LocalDateTime getDt() {
		return dt;
	}

	public void setDt(LocalDateTime dt) {
		this.dt = dt;
	}

	public WeatherMapTimeMainBean getMain() {
		return main;
	}

	public void setMain(WeatherMapTimeMainBean main) {
		this.main = main;
	}

	@JsonIgnore
	public Boolean isDaily() {
		return (this.dt.getHour() >= 6 && this.dt.getHour() < 18);
	}

}
