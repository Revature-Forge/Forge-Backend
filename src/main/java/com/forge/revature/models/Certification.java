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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "certification")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Certification {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "certificationid")
    private long id;

    @ManyToOne
    @JoinColumn(name = "portfolio_id", nullable = false)
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
