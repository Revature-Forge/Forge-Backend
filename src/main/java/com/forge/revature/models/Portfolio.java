package com.forge.revature.models;


import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

import com.forge.revature.converter.HashMapConverter;
import com.forge.revature.models.User;

import javax.persistence.Convert;

import javax.persistence.ManyToOne;
import javax.persistence.MapKey;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "portfolios")
@Getter
@Setter
public class Portfolio {
    
    public Portfolio(int id, String name, User user, boolean submitted, boolean approved, boolean reviewed,
            String feedback, HashMap<String, String> flags) {
        this.id = id;
        this.name = name;
        this.user = user;
        this.submitted = submitted;
        this.approved = approved;
        this.reviewed = reviewed;
        this.feedback = feedback;
        this.flags = flags;
    }

    public Portfolio() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    //Charles: I do not remember if I added this or Matthew added this but the tests pass if i comment it out
    // @OneToMany
    // private List<Certification> certifications;

    @Column
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

    @Column
    private String feedback;
    
//    @Column
//    //@Convert(converter = HashMapConverter.class)
//    @MapKey(name = "feedback")
//    private Map<String, String> flags = new HashMap<>();
    
    @Column
    @Convert(converter = HashMapConverter.class)
    private HashMap<String, String> flags;
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isSubmitted() {
        return submitted;
    }

    public void setSubmitted(boolean submitted) {
        this.submitted = submitted;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public boolean isReviewed() {
        return reviewed;
    }

    public void setReviewed(boolean reviewed) {
        this.reviewed = reviewed;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }


}
