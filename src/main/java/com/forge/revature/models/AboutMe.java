package com.forge.revature.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Max Lee 
 * @version 1.0
 * 
 * Represents the about me section for a portfolio.
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AboutMe {
    
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
	
    @OneToOne
    @JoinColumn(name = "portfolio_id")
    private Portfolio portfolio;
    
    @Column(length = 10000)
    private String bio;
    
    private String email;
    
    private String phone;

    public AboutMe(String bio, String email, String phone) {
        this.bio = bio;
        this.email = email;
        this.phone = phone;
    }

    public AboutMe(Portfolio portfolio, String bio, String email, String phone) {
        this.portfolio = portfolio;
        this.bio = bio;
        this.email = email;
        this.phone = phone;
    }
}
