package model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Measurement(Long timestamp, Long deviceID, Double measurementValue) {
    @JsonProperty("timestamp")
    public Long timestamp() { return timestamp; }

    @JsonProperty("deviceID")
    public Long deviceID() { return deviceID; }

    @JsonProperty("measurementValue")
    public Double measurementValue() { return measurementValue; }

}
