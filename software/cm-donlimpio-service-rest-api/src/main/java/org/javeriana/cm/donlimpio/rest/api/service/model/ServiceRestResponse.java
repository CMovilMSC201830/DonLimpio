package org.javeriana.cm.donlimpio.rest.api.service.model;

import lombok.Builder;
import lombok.Data;

@Data
public class ServiceRestResponse extends HttpRestResponse {
    private int serviceId;

    @Builder
    public ServiceRestResponse(boolean success, String message, int serviceId) {
        super(success, message);
        this.serviceId = serviceId;
    }
}
