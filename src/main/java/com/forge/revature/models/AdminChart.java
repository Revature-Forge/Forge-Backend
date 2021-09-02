package com.forge.revature.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdminChart {
	String name;
	int approved;
	int denied;
	double responseTime;
	String responseTimeString;
}