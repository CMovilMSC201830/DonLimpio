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
 * @author mpedrozoduran
 */
@Data
@Entity
@Table(name = "persona")
public class Persona implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "firstName")
    private String firstName;
    @Column(name = "lastName")
    private String lastName;
    @Column(name = "userPhoneNumber")
    private Long userPhoneNumber;
    @Column(name = "email")
    private String email;
    @Column(name = "create_time", insertable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Date createTime;
    @JoinColumn(name = "document_type", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private DocumentTypes documentTypes;
    @Column(name = "uuid", columnDefinition = "TEXT")
    private String uuid;

}
