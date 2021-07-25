package com.forge.revature.models;

import java.util.List;

public class FullPortfolio {
    private int id;
    private String name;
    private User user;
    private boolean submitted;
    private boolean approved;
    private boolean reviewed;
    private String feedback;
    private AboutMe aboutMe;
    private List<Certification> certifications;
    private List<Education> educations;
    private List<Equivalency> equivalencies;
    private List<GitHub> gitHubs;
    private List<Honor> honors;
    private List<Project> projects;
    private List<WorkExperience> workExperiences;
    private List<WorkHistory> workHistories;
    
    public FullPortfolio() {
    }

    public FullPortfolio(int id, String name, User user, boolean submitted, boolean approved, boolean reviewed,
            String feedback, AboutMe aboutMe, List<Certification> certifications, List<Education> educations,
            List<Equivalency> equivalencies, List<GitHub> gitHubs, List<Honor> honors, List<Project> projects,
            List<WorkExperience> workExperiences, List<WorkHistory> workHistories) {
        this.id = id;
        this.name = name;
        this.user = user;
        this.submitted = submitted;
        this.approved = approved;
        this.reviewed = reviewed;
        this.feedback = feedback;
        this.aboutMe = aboutMe;
        this.certifications = certifications;
        this.educations = educations;
        this.equivalencies = equivalencies;
        this.gitHubs = gitHubs;
        this.honors = honors;
        this.projects = projects;
        this.workExperiences = workExperiences;
        this.workHistories = workHistories;
    }
    
    

    public FullPortfolio(String name, boolean submitted, boolean approved, boolean reviewed, String feedback,
			AboutMe aboutMe, List<Certification> certifications, List<Education> educations,
			List<Equivalency> equivalencies, List<GitHub> gitHubs, List<Honor> honors, List<Project> projects,
			List<WorkExperience> workExperiences, List<WorkHistory> workHistories) {
		super();
		this.name = name;
		this.submitted = submitted;
		this.approved = approved;
		this.reviewed = reviewed;
		this.feedback = feedback;
		this.aboutMe = aboutMe;
		this.certifications = certifications;
		this.educations = educations;
		this.equivalencies = equivalencies;
		this.gitHubs = gitHubs;
		this.honors = honors;
		this.projects = projects;
		this.workExperiences = workExperiences;
		this.workHistories = workHistories;
	}

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

    public AboutMe getAboutMe() {
        return aboutMe;
    }

    public void setAboutMe(AboutMe aboutMe) {
        this.aboutMe = aboutMe;
    }

    public List<Certification> getCertifications() {
        return certifications;
    }

    public void setCertifications(List<Certification> certifications) {
        this.certifications = certifications;
    }

    public List<Education> getEducations() {
        return educations;
    }

    public void setEducations(List<Education> educations) {
        this.educations = educations;
    }

    public List<Equivalency> getEquivalencies() {
        return equivalencies;
    }

    public void setEquivalencies(List<Equivalency> equivalencies) {
        this.equivalencies = equivalencies;
    }

    public List<GitHub> getGitHubs() {
        return gitHubs;
    }

    public void setGitHubs(List<GitHub> gitHubs) {
        this.gitHubs = gitHubs;
    }

    public List<Honor> getHonors() {
        return honors;
    }

    public void setHonors(List<Honor> honors) {
        this.honors = honors;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    public List<WorkExperience> getWorkExperiences() {
        return workExperiences;
    }

    public void setWorkExperiences(List<WorkExperience> workExperiences) {
        this.workExperiences = workExperiences;
    }

    public List<WorkHistory> getWorkHistories() {
        return workHistories;
    }

    public void setWorkHistories(List<WorkHistory> workHistories) {
        this.workHistories = workHistories;
    }

    @Override
    public String toString() {
        return "FullPortfolio [aboutMe=" + aboutMe + ", approved=" + approved + ", certifications=" + certifications
                + ", educations=" + educations + ", equivalencies=" + equivalencies + ", feedback=" + feedback
                + ", gitHubs=" + gitHubs + ", honors=" + honors + ", id=" + id + ", name=" + name + ", projects="
                + projects + ", reviewed=" + reviewed + ", submitted=" + submitted + ", user=" + user
                + ", workExperiences=" + workExperiences + ", workHistories=" + workHistories + "]";
    }
}