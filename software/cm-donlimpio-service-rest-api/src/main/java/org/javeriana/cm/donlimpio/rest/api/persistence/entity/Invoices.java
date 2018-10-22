/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javeriana.cm.donlimpio.rest.api.persistence.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

/**
 *
 * @author mpedrozoduran
 */
@Data
@Entity
@Table(name = "invoices")
public class Invoices implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Column(name = "payment_method_id")
    private Integer paymentMethodId;
    @Column(name = "payment_total", columnDefinition = "DECIMAL")
    private Long paymentTotal;
    @Lob
    @Column(name = "comments", columnDefinition = "TEXT")
    private String comments;
    @OneToMany(mappedBy = "invoiceId")
    private Collection<Services> servicesCollection;
}
