package com.forge.revature.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "workexperiences")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class WorkExperience {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "workexperienceid")
    private int id;

    private String employer;

    private String title;

    @Column(length = 10000)
    private String responsibilities;

    @Column(length = 10000)
    private String description;

    @Column(length = 10000)
    private String technologies;

    @Temporal(TemporalType.DATE)
    private Date startDate;

    @Temporal(TemporalType.DATE)
    private Date endDate;

    @OneToOne
    @JoinColumn
    private Portfolio portfolio;

    public WorkExperience(String employer, String title, String responsibilities, String description,
            String technologies, Date startDate, Date endDate) {
        this.employer = employer;
        this.title = title;
        this.responsibilities = responsibilities;
        this.description = description;
        this.technologies = technologies;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public WorkExperience(String employer, String title, String responsibilities, String description,
            String technologies, Date startDate, Date endDate, Portfolio portfolio) {
        this.employer = employer;
        this.title = title;
        this.responsibilities = responsibilities;
        this.description = description;
        this.technologies = technologies;
        this.startDate = startDate;
        this.endDate = endDate;
        this.portfolio = portfolio;
    }

}
