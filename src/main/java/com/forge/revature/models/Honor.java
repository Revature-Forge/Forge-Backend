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
@Table(name = "honors")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Honor {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  private String title;

  private String description;

  private String dateReceived;

  private String receivedFrom;

  @ManyToOne
  @JoinColumn
  private Portfolio portfolio;

  public Honor(String title, String description, String dateReceived, String receivedFrom) {
    this.title = title;
    this.description = description;
    this.dateReceived = dateReceived;
    this.receivedFrom = receivedFrom;
  }

  public Honor(String title, String description, String dateReceived, String receivedFrom, Portfolio portfolio) {
    this.title = title;
    this.description = description;
    this.dateReceived = dateReceived;
    this.receivedFrom = receivedFrom;
    this.portfolio = portfolio;
  }

}