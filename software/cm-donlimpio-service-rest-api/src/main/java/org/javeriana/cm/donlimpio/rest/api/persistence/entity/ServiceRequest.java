package org.javeriana.cm.donlimpio.rest.api.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class ServiceRequest {
    private int categoryId;
    private int serviceStatus;
    private Date requestDate;
}
