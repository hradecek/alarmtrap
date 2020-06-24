package com.hradecek.alarms.mappings;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents alarm mapping defined in mapping files.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AlarmMapping {

    @JsonProperty("AdditionalInformation")
    private String additionalInformation;

    @JsonProperty("Description")
    private String description ;

    @JsonProperty("ProbableCause")
    private String probableCause;

    @JsonProperty("AlarmType")
    private String alarmType;

    @JsonProperty("Severity")
    private Severity severity;

    @JsonProperty("Component")
    private String component;

    @JsonProperty("Name")
    private String name;

    public String getAdditionalInformation() {
        return additionalInformation;
    }

    public String getDescription() {
        return description;
    }

    public String getProbableCause() {
        return probableCause;
    }

    public String getAlarmType() {
        return alarmType;
    }

    public Severity getSeverity() {
        return severity;
    }

    public String getComponent() {
        return component;
    }

    public String getName() {
        return name;
    }
}
