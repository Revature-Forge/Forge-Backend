package com.forge.revature.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "workhistory")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class WorkHistory {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  private String title;

  private String employer;

  private String responsibilities;

  private String description;

  private String tools;

  private String startDate;

  private String endDate;

  @ManyToOne
  @JoinColumn
  private Portfolio portfolio;

  public WorkHistory(String title, String employer, String responsibilities, String description, String tools, String startDate, String endDate) {
    this.title = title;
    this.employer = employer;
    this.responsibilities = responsibilities;
    this.description = description;
    this.tools = tools;
    this.startDate = startDate;
    this.endDate = endDate;
  }

  public WorkHistory(String title, String employer, String responsibilities, String description, String tools,
      String startDate, String endDate, Portfolio portfolio) {
    this.title = title;
    this.employer = employer;
    this.responsibilities = responsibilities;
    this.description = description;
    this.tools = tools;
    this.startDate = startDate;
    this.endDate = endDate;
    this.portfolio = portfolio;
  }

}