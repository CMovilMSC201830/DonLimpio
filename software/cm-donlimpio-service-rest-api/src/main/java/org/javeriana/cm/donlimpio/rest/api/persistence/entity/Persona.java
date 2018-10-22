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
import java.util.Date;

/**
 *
 * @author mpedrozoduran
 */
@Data
@Entity
@Table(name = "persona")
public class Persona implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @Column(name = "document_id")
    private String documentId;
    @Basic(optional = false)
    @Column(name = "email")
    private String email;
    @Basic(optional = false)
    @Column(name = "username")
    private String username;
    @Basic(optional = false)
    @Column(name = "password")
    private String password;
    @Basic(optional = false)
    @Column(name = "create_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    @Lob
    @Column(name = "user_picture", columnDefinition = "TEXT")
    private String userPicture;
    @JoinTable(name = "persona_profiles", joinColumns = {
        @JoinColumn(name = "persona_id", referencedColumnName = "id")}, inverseJoinColumns = {
        @JoinColumn(name = "profile_id", referencedColumnName = "id")})
    @ManyToMany
    private Collection<Profiles> profilesCollection;
    @JoinColumn(name = "document_type", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private DocumentTypes documentType;
    @OneToMany(mappedBy = "clientId")
    private Collection<Services> servicesCollection;
    @OneToMany(mappedBy = "personaId")
    private Collection<PersonaAddresses> personaAddressesCollection;
    
}
