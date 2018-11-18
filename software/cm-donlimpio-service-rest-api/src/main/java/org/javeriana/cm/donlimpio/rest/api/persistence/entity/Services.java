/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javeriana.cm.donlimpio.rest.api.persistence.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author mpedrozoduran
 */
@Data
@Entity
@Table(name = "ordered_services")
public class Services implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "professional_id")
    private String professional;
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    @ManyToOne
    private Categories category;
    @JoinColumn(name = "invoice_id", referencedColumnName = "id")
    @ManyToOne(cascade = CascadeType.ALL)
    private Invoices invoice;
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    @ManyToOne(cascade = CascadeType.ALL)
    private Persona persona;
    @JoinColumn(name = "persona_address_id", referencedColumnName = "id")
    @ManyToOne(cascade = CascadeType.ALL)
    private PersonaAddresses personaAddress;
    @Column(name = "reservation_date")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Date reservationDate;
    @Column(name = "status")
    private Integer status;
}
