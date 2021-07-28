package com.forge.revature.models;

import java.util.HashMap;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FullPortfolio {

	private int id;
	private String name;
	private User user;
	private boolean submitted;
	private boolean approved;
	private boolean reviewed;
	private String feedback;
	private HashMap<String, String> flags;
	private AboutMe aboutMe;
	private List<Certification> certifications;
	private List<Education> educations;
	private List<Equivalency> equivalencies;
	private List<GitHub> gitHubs;
	private List<Honor> honors;
	private List<Project> projects;
	private List<WorkExperience> workExperiences;
	private List<WorkHistory> workHistories;
  private List<Matrix> matrices;

	public FullPortfolio(String name, User user, boolean submitted, boolean approved, boolean reviewed, String feedback,
			HashMap<String, String> flags, AboutMe aboutMe, List<Certification> certifications,
			List<Education> educations, List<Equivalency> equivalencies, List<GitHub> gitHubs, List<Honor> honors,
			List<Project> projects, List<WorkExperience> workExperiences, List<WorkHistory> workHistories, List<Matrix> matrices) {

		super();
		this.name = name;
		this.user = user;
		this.submitted = submitted;
		this.approved = approved;
		this.reviewed = reviewed;
		this.feedback = feedback;
		this.flags = flags;
		this.aboutMe = aboutMe;
		this.certifications = certifications;
		this.educations = educations;
		this.equivalencies = equivalencies;
		this.gitHubs = gitHubs;
		this.honors = honors;
		this.projects = projects;
		this.workExperiences = workExperiences;
		this.workHistories = workHistories;
		this.matrices = matrices;
	}
}
