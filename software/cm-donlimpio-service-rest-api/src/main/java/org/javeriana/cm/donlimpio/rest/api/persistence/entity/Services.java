/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javeriana.cm.donlimpio.rest.api.persistence.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 *
 * @author mpedrozoduran
 */
@Data
@Entity
@Table(name = "services")
public class Services implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "professional_id")
    private String professionalId;
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    @ManyToOne
    private Categories categoryId;
    @JoinColumn(name = "invoice_id", referencedColumnName = "id")
    @ManyToOne
    private Invoices invoiceId;
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    @ManyToOne
    private Persona clientId;
    @JoinColumn(name = "persona_address_id", referencedColumnName = "id")
    @ManyToOne
    private PersonaAddresses personaAddressId;
}
