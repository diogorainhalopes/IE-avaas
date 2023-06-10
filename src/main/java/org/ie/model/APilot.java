package org.ie.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

public class APilot {

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

    @JsonProperty("GeneralWeather") // (Rain, Snow, Extreme etc.)
    private String generalWeather;

    @JsonProperty("ApplyBrakes") // yes/no
    private String applyBrakes;

    @JsonProperty("RegenerativeBraking") // yes/no
    private String regenerativeBraking;

    @JsonProperty("SugestStop") // yes/no
    private String sugestStop;

    @JsonProperty("Danger") // yes/no
    private String danger;

    @JsonProperty("Visibility") // (Excellent/Good/Decent/Bad/Terrible)
    private String visibility;

    @JsonProperty("HeadlightsLevel") // Adjust according to EnvironmentalLightning
    private String headlightsLevel;

    @JsonProperty("ChargeAv") // yes/no
    private String chargeAv;

    public APilot() {

    }

    public APilot(String timeStamp, String avId, String speed, String batteryLevel, String driverTirenessLevel,
            String location, String environmentalLightning, String rainConditions, String fogConditions,
            String tractionWheelsLevel, String trafficLight, String obstacleProximity, String pedestrianProximity,
            String averageConsumptionLevel, String generalWeather, String applyBrakes, String regenerativeBraking,
            String sugestStop, String danger, String visibility, String headlightsLevel, String chargeAv) {
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
        this.generalWeather = generalWeather;
        this.applyBrakes = applyBrakes;
        this.regenerativeBraking = regenerativeBraking;
        this.sugestStop = sugestStop;
        this.danger = danger;
        this.visibility = visibility;
        this.headlightsLevel = headlightsLevel;
        this.chargeAv = chargeAv;
    }

    public String toJson() {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        try {
            return ow.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return ow.toString();
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

    public String getTrafficLight() {
        return trafficLight;
    }

    public void setTrafficLight(String trafficLight) {
        this.trafficLight = trafficLight;
    }

    public String getObstacleProximity() {
        return obstacleProximity;
    }

    public void setObstacleProximity(String obstacleProximity) {
        this.obstacleProximity = obstacleProximity;
    }

    public String getPedestrianProximity() {
        return pedestrianProximity;
    }

    public void setPedestrianProximity(String pedestrianProximity) {
        this.pedestrianProximity = pedestrianProximity;
    }

    public String getAverageConsumptionLevel() {
        return averageConsumptionLevel;
    }

    public void setAverageConsumptionLevel(String averageConsumptionLevel) {
        this.averageConsumptionLevel = averageConsumptionLevel;
    }

    public String getGeneralWeather() {
        return generalWeather;
    }

    public void setGeneralWeather(String generalWeather) {
        this.generalWeather = generalWeather;
    }

    public String getApplyBrakes() {
        return applyBrakes;
    }

    public void setApplyBrakes(String applyBrakes) {
        this.applyBrakes = applyBrakes;
    }

    public String getRegenerativeBraking() {
        return regenerativeBraking;
    }

    public void setRegenerativeBraking(String regenerativeBraking) {
        this.regenerativeBraking = regenerativeBraking;
    }

    public String getSugestStop() {
        return sugestStop;
    }

    public void setSugestStop(String sugestStop) {
        this.sugestStop = sugestStop;
    }

    public String getDanger() {
        return danger;
    }

    public void setDanger(String danger) {
        this.danger = danger;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public String getHeadlightsLevel() {
        return headlightsLevel;
    }

    public void setHeadlightsLevel(String headlightsLevel) {
        this.headlightsLevel = headlightsLevel;
    }

    public String getChargeAv() {
        return chargeAv;
    }

    public void setChargeAv(String chargeAv) {
        this.chargeAv = chargeAv;
    }

}
