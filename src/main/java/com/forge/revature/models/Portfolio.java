package com.forge.revature.models;

import java.util.HashMap;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.forge.revature.converter.HashMapConverter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "portfolios")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
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
	
	@ManyToOne
	@JoinColumn(name = "admin_id")
	private User admin;

	private String feedback;

	@Column
	@Convert(converter = HashMapConverter.class)
	private HashMap<String, String> flags;

	public Portfolio(String name, boolean submitted, boolean approved, boolean reviewed, String feedback) {
		this.name = name;
		this.submitted = submitted;
		this.approved = approved;
		this.reviewed = reviewed;
		this.feedback = feedback;
	}

	public Portfolio(int id, String name, User user, boolean submitted, boolean approved, boolean reviewed,
			String feedback, HashMap<String, String> flags) {
		super();
		this.id = id;
		this.name = name;
		this.user = user;
		this.submitted = submitted;
		this.approved = approved;
		this.reviewed = reviewed;
		this.feedback = feedback;
		this.flags = flags;
	}

}
