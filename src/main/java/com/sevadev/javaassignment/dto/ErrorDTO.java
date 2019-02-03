package com.sevadev.javaassignment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class ErrorDTO implements Serializable {
    @JsonProperty("Status Code")
    private Integer statusCode;
    @JsonProperty("Message")
    private String message;
}
