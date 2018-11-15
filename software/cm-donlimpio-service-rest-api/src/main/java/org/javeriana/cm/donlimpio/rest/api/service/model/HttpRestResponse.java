package org.javeriana.cm.donlimpio.rest.api.service.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HttpRestResponse {
    private boolean success;
    private String message;
}
