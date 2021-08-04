package com.forge.revature.controllers;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.forge.revature.models.Portfolio;

public abstract class PortfolioIgnoreMixin {
    @JsonIgnore
    Portfolio portfolio;
}
