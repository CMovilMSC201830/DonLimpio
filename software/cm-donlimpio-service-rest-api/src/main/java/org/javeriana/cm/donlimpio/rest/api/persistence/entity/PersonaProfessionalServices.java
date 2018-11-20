/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javeriana.cm.donlimpio.rest.api.persistence.entity;

import lombok.Data;

import java.io.Serializable;

/**
 *
 * @author mpedrozoduran
 */
@Data
public class PersonaProfessionalServices extends ProfessionalServices implements Serializable {

    private static final long serialVersionUID = 1L;
    private String uuidUser;
}
