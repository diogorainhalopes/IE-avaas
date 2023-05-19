package org.ie.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AvEvent {

	@JsonProperty("TimeStamp")
	private String timeStamp;

	@JsonProperty("AV_ID")
	private String avId;

	@JsonProperty("Speed")
	private String speed;

	@JsonProperty("BatteryLevel")
	private String batteryLevel;

	@JsonProperty("DriverTirenessLevel")
	private String driverTirenessLevel;

	@JsonProperty("Location")
	private String location;

	@JsonProperty("EnvironmentalLightning")
	private String environmentalLightning;

	@JsonProperty("RainConditions")
	private String rainConditions;

	@JsonProperty("FogConditions")
	private String fogConditions;

	@JsonProperty("TractionWheelsLevel")
	private String tractionWheelsLevel;

	@JsonProperty("TrafficLight")
	private String trafficLight;

	@JsonProperty("ObstacleProximity")
	private String obstacleProximity;

	@JsonProperty("PedestrianProximity")
	private String pedestrianProximity;

	@JsonProperty("AverageConsumptionLevel")
	private String averageConsumptionLevel;

	public AvEvent() {

	}

	public AvEvent(String timeStamp, String avId, String speed, String batteryLevel, String driverTirenessLevel,
			String location, String environmentalLightning, String rainConditions, String fogConditions,
			String tractionWheelsLevel, String trafficLight, String obstacleProximity, String pedestrianProximity,
			String averageConsumptionLevel) {
		this.timeStamp = timeStamp;
		this.avId = avId;
		this.speed = speed;
		this.batteryLevel = batteryLevel;
		this.driverTirenessLevel = driverTirenessLevel;
		this.location = location;
		this.environmentalLightning = environmentalLightning;
		this.rainConditions = rainConditions;
		this.fogConditions = fogConditions;
		this.tractionWheelsLevel = tractionWheelsLevel;
		this.trafficLight = trafficLight;
		this.obstacleProximity = obstacleProximity;
		this.pedestrianProximity = pedestrianProximity;
		this.averageConsumptionLevel = averageConsumptionLevel;
	}

	@Override
	public String toString() {
		return "{ \"AV_Event\": { \"TimeStamp\":" + timeStamp + ", \"AV_ID\":" + avId + ", \"Speed\":" + speed
				+ ", \"BatteryLevel\":"
				+ batteryLevel + ", \"DriverTirenessLevel\":" + driverTirenessLevel + ", \"Location\":" + location
				+ ", \"EnvironmentalLightning\":" + environmentalLightning + ", \"RainConditions\":" + rainConditions
				+ ", \"FogConditions\":" + fogConditions + ", \"TractionWheelsLevel\":" + tractionWheelsLevel
				+ ", \"TrafficLight\":" + trafficLight + ", \"ObstacleProximity\":" + obstacleProximity
				+ ", \"PedestrianProximity\":" + pedestrianProximity + ", \"averageConsumptionLevel\":"
				+ averageConsumptionLevel + "} }";
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getAvId() {
		return avId;
	}

	public void setAvId(String avId) {
		this.avId = avId;
	}

	public String getSpeed() {
		return speed;
	}

	public void setSpeed(String speed) {
		this.speed = speed;
	}

	public String getBatteryLevel() {
		return batteryLevel;
	}

	public void setBatteryLevel(String batteryLevel) {
		this.batteryLevel = batteryLevel;
	}

	public String getDriverTirenessLevel() {
		return driverTirenessLevel;
	}

	public void setDriverTirenessLevel(String driverTirenessLevel) {
		this.driverTirenessLevel = driverTirenessLevel;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getEnvironmentalLightning() {
		return environmentalLightning;
	}

	public void setEnvironmentalLightning(String environmentalLightning) {
		this.environmentalLightning = environmentalLightning;
	}

	public String getRainConditions() {
		return rainConditions;
	}

	public void setRainConditions(String rainConditions) {
		this.rainConditions = rainConditions;
	}

	public String getFogConditions() {
		return fogConditions;
	}

	public void setFogConditions(String fogConditions) {
		this.fogConditions = fogConditions;
	}

	public String getTractionWheelsLevel() {
		return tractionWheelsLevel;
	}

	public void setTractionWheelsLevel(String tractionWheelsLevel) {
		this.tractionWheelsLevel = tractionWheelsLevel;
	}

}
