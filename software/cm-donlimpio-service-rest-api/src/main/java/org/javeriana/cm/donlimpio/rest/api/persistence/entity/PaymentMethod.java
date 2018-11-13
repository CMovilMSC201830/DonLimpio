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
@Table(name = "payment_methods")
public class PaymentMethod implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "type")
    private String type;
    @Column(name = "valid_thru")
    private int validThru;
    @Column(name = "number")
    private String number;
    @Column(name = "ccv")
    private String ccv;
    @Column(name = "card_holder")
    private String cardHolder;
    @Column(name = "franchise")
    private String franchise;
}
