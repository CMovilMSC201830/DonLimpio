package org.javeriana.cm.donlimpio.rest.api.service.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HttpRestResponse {
    private boolean success;
    private String message;
}
