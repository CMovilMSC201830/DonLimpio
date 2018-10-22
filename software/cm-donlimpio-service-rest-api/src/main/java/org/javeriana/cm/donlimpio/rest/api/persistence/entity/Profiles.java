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
@Table(name = "profiles")
public class Profiles implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "name")
    private String name;
    @ManyToMany(mappedBy = "profilesCollection")
    private Collection<Persona> personaCollection;
}
