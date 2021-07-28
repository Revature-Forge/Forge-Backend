package com.forge.revature.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

/**
 * @author Max Lee 
 * @version 1.0
 * 
 * Represents the education section for a portfolio. One portfolio can have multiple education sections.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Education {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @ManyToOne
    @JoinColumn(name = "portfolio_id")
    private Portfolio portfolio;
    
    private String university;
    
    private String degree;
    
    private String graduationDate;
    
    private double gpa;
    
    private String logoUrl;

    public Education(String university, String degree, String graduationDate, double gpa, String logoUrl) {
        this.university = university;
        this.degree = degree;
        this.graduationDate = graduationDate;
        this.gpa = gpa;
        this.logoUrl = logoUrl;
    }

    public Education(Portfolio portfolio, String university, String degree, String graduationDate, double gpa, String logoUrl) {
        this.portfolio = portfolio;
        this.university = university;
        this.degree = degree;
        this.graduationDate = graduationDate;
        this.gpa = gpa;
        this.logoUrl = logoUrl;
    }
}
