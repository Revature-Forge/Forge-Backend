package com.forge.revature.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "certifications")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Certification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "certificationid")
    private int id;

    @ManyToOne
    @JoinColumn(name = "portfolio_id")
    private Portfolio portfolio;

    private String name;

    private String certId;

    private String issuedBy;

    @Temporal(TemporalType.DATE)
    private Date issuedOn;

    private String publicUrl;

    public Certification(String name, String certId, String issuedBy, Date issuedOn, String publicUrl) {
        this.name = name;
        this.certId = certId;
        this.issuedBy = issuedBy;
        this.issuedOn = issuedOn;
        this.publicUrl = publicUrl;
    }
    
    public Certification(Portfolio portfolio, String name, String certId, String issuedBy, Date issuedOn, String publicUrl) {
        this.portfolio = portfolio;
        this.name = name;
        this.certId = certId;
        this.issuedBy = issuedBy;
        this.issuedOn = issuedOn;
        this.publicUrl = publicUrl;
    }

}
