package org.ie.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

public class AvResult {

	@JsonProperty("TimeStamp")
	private String timeStamp;

	@JsonProperty("AV_ID")
	private String avId;

	@JsonProperty("Speed")
	private String speed;

	@JsonProperty("ICT_Infrastructure")
	private String ictInfrastructure;

	@JsonProperty("RoadConditions")
	private String roadConditions;

	@JsonProperty("Security")
	private String security;

	@JsonProperty("Utilitarianism")
	private String utilitarianism;

	@JsonProperty("Legitimacy")
	private String legitimacy;

	@JsonProperty("SocialResponsibility")
	private String socialResponsibility;

	@JsonProperty("Detection")
	private String detection;

	@JsonProperty("Identification")
	private String identification;

	@JsonProperty("RiskAnalysis")
	private String riskAnalysis;

	@JsonProperty("Reaction")
	private String reaction;

	@JsonProperty("Execution")
	private String execution;

	public AvResult(String timeStamp, String avId, String speed, String ictInfrastructure, String roadConditions,
			String security, String utilitarianism, String legitimacy, String socialResponsibility, String detection,
			String identification, String riskAnalysis, String reaction, String execution) {
		this.timeStamp = timeStamp;
		this.avId = avId;
		this.speed = speed;
		this.ictInfrastructure = ictInfrastructure;
		this.roadConditions = roadConditions;
		this.security = security;
		this.utilitarianism = utilitarianism;
		this.legitimacy = legitimacy;
		this.socialResponsibility = socialResponsibility;
		this.detection = detection;
		this.identification = identification;
		this.riskAnalysis = riskAnalysis;
		this.reaction = reaction;
		this.execution = execution;
	}

	public AvResult() {
		// Does nothing
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

	public String getIctInfrastructure() {
		return ictInfrastructure;
	}

	public void setIctInfrastructure(String ictInfrastructure) {
		this.ictInfrastructure = ictInfrastructure;
	}

	public String getRoadConditions() {
		return roadConditions;
	}

	public void setRoadConditions(String roadConditions) {
		this.roadConditions = roadConditions;
	}

	public String getSecurity() {
		return security;
	}

	public void setSecurity(String security) {
		this.security = security;
	}

	public String getUtilitarianism() {
		return utilitarianism;
	}

	public void setUtilitarianism(String utilitarianism) {
		this.utilitarianism = utilitarianism;
	}

	public String getLegitimacy() {
		return legitimacy;
	}

	public void setLegitimacy(String legitimacy) {
		this.legitimacy = legitimacy;
	}

	public String getSocialResponsibility() {
		return socialResponsibility;
	}

	public void setSocialResponsibility(String socialResponsibility) {
		this.socialResponsibility = socialResponsibility;
	}

	public String getDetection() {
		return detection;
	}

	public void setDetection(String detection) {
		this.detection = detection;
	}

	public String getIdentification() {
		return identification;
	}

	public void setIdentification(String identification) {
		this.identification = identification;
	}

	public String getRiskAnalysis() {
		return riskAnalysis;
	}

	public void setRiskAnalysis(String riskAnalysis) {
		this.riskAnalysis = riskAnalysis;
	}

	public String getReaction() {
		return reaction;
	}

	public void setReaction(String reaction) {
		this.reaction = reaction;
	}

	public String getExecution() {
		return execution;
	}

	public void setExecution(String execution) {
		this.execution = execution;
	}

}
