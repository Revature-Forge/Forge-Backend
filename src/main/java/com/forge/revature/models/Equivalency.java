package com.forge.revature.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "equivalencies")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Equivalency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String header;

    private int value;

    @ManyToOne
    @JoinColumn(name = "portfolio_id")
    private Portfolio portfolio;

    public Equivalency(String header, int value, Portfolio portfolio) {
        this.header = header;
        this.value = value;
        this.portfolio = portfolio;
    }
    
    public Equivalency(String header, int value) {
    	this.header = header;
    	this.value = value;
    }

	@Override
	public String toString() {
		return "Equivalency [id=" + id + ", header=" + header + ", value=" + value + ", portfolio=" + portfolio + "]";
	}
    
    

}