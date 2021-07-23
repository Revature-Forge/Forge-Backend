package com.forge.revature.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String fName;

    private String lName;

    private String email;

    private String password;

    private boolean admin;

	public User(String fName, String lName, String email, String password, boolean admin) {
		super();
		this.fName = fName;
		this.lName = lName;
		this.email = email;
		this.password = password;
		this.admin = admin;
	}

}
