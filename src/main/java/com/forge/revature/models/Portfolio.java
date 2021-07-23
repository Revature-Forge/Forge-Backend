package com.forge.revature.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "portfolios")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Portfolio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(columnDefinition = "boolean default false")
    private boolean submitted;

    @Column(columnDefinition = "boolean default false")
    private boolean approved;

    @Column(columnDefinition = "boolean default false")
    private boolean reviewed;

    private String feedback;
    
    public Portfolio(String name, boolean submitted, boolean approved, boolean reviewed, String feedback) {
    	this.name = name;
    	this.submitted = submitted;
    	this.approved = approved;
    	this.reviewed = reviewed;
    	this.feedback = feedback;
    }

}
