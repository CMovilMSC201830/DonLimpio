package org.javeriana.cm.donlimpio.rest.api.service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder(builderMethodName = "parentBuilder")
public class HttpRestResponse {
    private boolean success;
    private String message;
}
