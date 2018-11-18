package org.javeriana.cm.donlimpio.rest.api.service.model;

import lombok.Builder;
import lombok.Data;

@Data
public class PersonaRestResponse extends HttpRestResponse {
    private long personaId;

    @Builder
    public PersonaRestResponse(boolean success, String message, long personaId) {
        super(success, message);
        this.personaId = personaId;
    }
}
