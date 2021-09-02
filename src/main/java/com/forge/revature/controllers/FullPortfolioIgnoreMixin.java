package com.forge.revature.controllers;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.forge.revature.models.User;
import java.util.HashMap;

public abstract class FullPortfolioIgnoreMixin {
	@JsonIgnore
	User user;
	
	@JsonIgnore
	HashMap<String, String> flags;
}
