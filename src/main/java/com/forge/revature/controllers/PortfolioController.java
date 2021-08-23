package com.forge.revature.controllers;

import java.time.DayOfWeek;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.servlet.http.HttpServletResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.forge.revature.models.*;
import com.forge.revature.repo.*;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("api/portfolios")
@NoArgsConstructor
@AllArgsConstructor
public class PortfolioController {
    @Autowired
    PortfolioRepo portRepo;

    @Autowired
    AboutMeRepo aboutMeRepo;

    @Autowired
    CertificationRepo certificationRepo;

    @Autowired
    EducationRepo educationRepo;

    @Autowired
    EquivalencyRepo equivalencyRepo;

    @Autowired
    GitHubRepo gitHubRepo;

    @Autowired
    HonorRepo honorRepo;

    @Autowired
    ProjectRepo projectRepo;

    @Autowired
    WorkExperienceRepo workExperienceRepo;

    @Autowired
    WorkHistoryRepo workHistoryRepo;
    
    @Autowired
    MatrixRepo matrixRepo;
    
    @Autowired
    SkillRepo skillRepo;

    public PortfolioController(PortfolioRepo portRepo) {
        this.portRepo = portRepo;
    }

    @GetMapping
    public List<Portfolio> getAll(){
        List<Portfolio> ports = StreamSupport.stream(portRepo.findAll().spliterator(), false)
        .collect(Collectors.toList());
    return ports;
    }

    @GetMapping("/{id}")
    public Portfolio getByID(@PathVariable(name = "id") int id){
        return portRepo.findById(id).get();
    }

    @GetMapping("/users/all/{id}")
    public List<Portfolio> getPortfoliosByUserId(@PathVariable int id){
        List<Portfolio> portfolios = portRepo.findAllByUserId(id);
        return portfolios;
    }

    @PostMapping
    public Portfolio postPort(@RequestBody Portfolio port){
        return portRepo.save(port);
    }
    
	double calculateAverageResponseTime()
	{
		List<Portfolio> portfolios =  getAll();
		if(portfolios.size() == 0)
		{
			return -1.0;
		}
		
		double average = 0;
		for(int i = 0; i < portfolios.size(); ++i)
		{
			average += portfolios.get(i).getResponseTime();
		}
		average /= portfolios.size();
		return average;
	}
    
    
    
    Long calculateResponseTime(ZonedDateTime modifiedSubmissionTime, ZonedDateTime modifiedReviewTime)
    {
	
	
		Instant timeCounter = Instant.ofEpochMilli(0);
	
		//if outside work hours move the modifiedReviewTime forward to the nearest work time
		//if inside work hours move it forward to the nearest hour and subtract that time from
		//the counter
		if(modifiedSubmissionTime.toEpochSecond() > modifiedReviewTime.toEpochSecond())
		{
			// in case of invalid inputs (where review time is after submission time)
			return -1L;
		}
		
		if(modifiedReviewTime.getHour() > 17)
		{
			modifiedReviewTime = modifiedReviewTime.truncatedTo(ChronoUnit.DAYS);
			modifiedReviewTime = modifiedReviewTime.plusDays(1);
		}
		if(modifiedReviewTime.getDayOfWeek() == DayOfWeek.SATURDAY)
		{
			modifiedReviewTime = modifiedReviewTime.truncatedTo(ChronoUnit.DAYS);
			modifiedReviewTime = modifiedReviewTime.plusDays(2);	
		}    		
		if(modifiedReviewTime.getDayOfWeek() == DayOfWeek.SUNDAY)
		{
			modifiedReviewTime = modifiedReviewTime.truncatedTo(ChronoUnit.DAYS);
			modifiedReviewTime = modifiedReviewTime.plusDays(1);	
		}
		if(modifiedReviewTime.getHour() < 10)
		{
			modifiedReviewTime = modifiedReviewTime.truncatedTo(ChronoUnit.DAYS);
			modifiedReviewTime = modifiedReviewTime.plusHours(10);
		}else{
	    ZonedDateTime roundedReviewTime = modifiedReviewTime.truncatedTo(ChronoUnit.HOURS);
	    roundedReviewTime = roundedReviewTime.plusHours(1);
		timeCounter = timeCounter.minusSeconds(roundedReviewTime.toEpochSecond() - modifiedReviewTime.toEpochSecond());
		modifiedReviewTime = roundedReviewTime;
		}    	
		
		//if outside work hours move the modifiedSubmissionTime forward to the nearest work time
		//if inside work hours move it forward to the nearest hour and add that time to the counter
		if(modifiedSubmissionTime.getHour() > 17)
		{
			modifiedSubmissionTime = modifiedSubmissionTime.truncatedTo(ChronoUnit.DAYS);
			modifiedSubmissionTime = modifiedSubmissionTime.plusDays(1);
		}
		if(modifiedSubmissionTime.getDayOfWeek() == DayOfWeek.SATURDAY)
		{
			modifiedSubmissionTime = modifiedSubmissionTime.truncatedTo(ChronoUnit.DAYS);
			modifiedSubmissionTime = modifiedSubmissionTime.plusDays(1);	
		}    		
		if(modifiedSubmissionTime.getDayOfWeek() == DayOfWeek.SUNDAY)
		{
			modifiedSubmissionTime = modifiedSubmissionTime.truncatedTo(ChronoUnit.DAYS);
			modifiedSubmissionTime = modifiedSubmissionTime.plusDays(1);	
		}
		if(modifiedSubmissionTime.getHour() < 10)
		{
			modifiedSubmissionTime = modifiedSubmissionTime.truncatedTo(ChronoUnit.DAYS);
			modifiedSubmissionTime = modifiedSubmissionTime.plusHours(10);
		}else{
	    	ZonedDateTime roundedSubmissionTime = modifiedSubmissionTime.truncatedTo(ChronoUnit.HOURS);
	    	roundedSubmissionTime = roundedSubmissionTime.plusHours(1);
			timeCounter = timeCounter.plusSeconds(roundedSubmissionTime.toEpochSecond() - modifiedSubmissionTime.toEpochSecond());
			modifiedSubmissionTime = roundedSubmissionTime;
		}
	
	
		//while modifiedSubmissionTime time is less than modifiedReviewTime increment modifiedSubmissionTime time by one hour
		//adding to the count if the time added is within work hours until it equals the modifiedReviewTime 
		while(modifiedSubmissionTime.toEpochSecond() < modifiedReviewTime.toEpochSecond())
		{
			//skipping time outside of work hours which is okay because we made sure both are in work hours
	    	if(modifiedSubmissionTime.getHour() > 17)
	    	{
	    		modifiedSubmissionTime = modifiedSubmissionTime.truncatedTo(ChronoUnit.DAYS);
	    		modifiedSubmissionTime = modifiedSubmissionTime.plusDays(1);
	    	}
	    	if(modifiedSubmissionTime.getDayOfWeek() == DayOfWeek.SATURDAY)
	    	{
	    		modifiedSubmissionTime = modifiedSubmissionTime.truncatedTo(ChronoUnit.DAYS);
	    		modifiedSubmissionTime = modifiedSubmissionTime.plusDays(1);	
	    	}    		
	    	if(modifiedSubmissionTime.getDayOfWeek() == DayOfWeek.SUNDAY)
	    	{
	    		modifiedSubmissionTime = modifiedSubmissionTime.truncatedTo(ChronoUnit.DAYS);
	    		modifiedSubmissionTime = modifiedSubmissionTime.plusDays(1);	
	    	}
	    	if(modifiedSubmissionTime.getHour() < 10)
	    	{
	    		modifiedSubmissionTime = modifiedSubmissionTime.truncatedTo(ChronoUnit.DAYS);
	    		modifiedSubmissionTime = modifiedSubmissionTime.plusHours(10);
	    	}
			//incrementing
	    	while((modifiedSubmissionTime.toEpochSecond() < modifiedReviewTime.toEpochSecond())&&
	    	(modifiedSubmissionTime.getHour() < 18))
	    	{
	    		timeCounter = timeCounter.plusSeconds(3600);
	    		modifiedSubmissionTime = modifiedSubmissionTime.plusHours(1);
	    	}
		}
		return timeCounter.getEpochSecond();
    }

	
    
    @PostMapping("/{id}")
    public void updateUser(@PathVariable int id , @RequestBody Portfolio updated){
    	Optional<Portfolio> old = portRepo.findById(id);
        
        if(old.isPresent()){
            if(updated.isSubmissionTrigger() == true)
            {
            	Instant currentTime = Instant.now();
            	ZoneId eastern = ZoneId.of("US/Eastern");
            	ZonedDateTime submissionTime = ZonedDateTime.ofInstant(currentTime, eastern);
            	submissionTime = submissionTime.truncatedTo(ChronoUnit.SECONDS);
            	old.get().setSubmissionTime(submissionTime.toString());
            }
            
            if( updated.isReviewTrigger() == true)
            {
            	
            	Instant nowUtc = Instant.now();
            	ZoneId eastern = ZoneId.of("US/Eastern");
            	ZonedDateTime reviewTime = ZonedDateTime.ofInstant(nowUtc, eastern);
            	reviewTime = reviewTime.truncatedTo(ChronoUnit.SECONDS);
            	old.get().setReviewTime(reviewTime.toString());
            	// do responseTime calculation
            	ZonedDateTime modifiedReviewTime = reviewTime;
            	ZonedDateTime modifiedSubmissionTime = ZonedDateTime.parse(old.get().getSubmissionTime());
            	long responseTime = calculateResponseTime(modifiedSubmissionTime, modifiedReviewTime);
            	old.get().setResponseTime(responseTime);
            }
            old.get().setApproved(updated.isApproved());
            old.get().setFeedback(updated.getFeedback());
            old.get().setName(updated.getName());
            old.get().setReviewed(updated.isReviewed());
            old.get().setSubmitted(updated.isSubmitted());
            old.get().setFlags(updated.getFlags());
            portRepo.save(old.get());
        }
    }
    
    @DeleteMapping("/{id}")
    @Transactional
    public Map<String, Boolean> deletePortfolio(@PathVariable int id) throws ResourceNotFoundException{
        Optional<Portfolio> port = portRepo.findById(id);

        if(port.isPresent()){
        	aboutMeRepo.deleteByPortfolioId(id);
        	certificationRepo.deleteByPortfolioId(id);
        	educationRepo.deleteByPortfolioId(id);
        	equivalencyRepo.deleteByPortfolioId(id);
        	gitHubRepo.deleteByPortfolioId(id);
        	honorRepo.deleteByPortfolioId(id);
        	projectRepo.deleteByPortfolioId(id);
        	workExperienceRepo.deleteByPortfolioId(id);
        	workHistoryRepo.deleteByPortfolioId(id);
        	
        	List<Matrix> m = matrixRepo.findAllByPortfolio(port.get());
        	m.forEach(s -> skillRepo.deleteByMatrix(s));
        	matrixRepo.deleteByPortfolioId(id);
            
        	portRepo.delete(port.get());
            
        }else{
            throw new ResourceNotFoundException("The Portfolio to be deleted could not be found");
        }

        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

    @GetMapping(value = "/full/{id}", produces = "application/octet-stream")
    public ResponseEntity<ByteArrayResource> getFullPortfolio(@PathVariable int id, HttpServletResponse response) throws JsonProcessingException {
        if (!portRepo.existsById(id)) return null;
        Portfolio port = portRepo.findById(id).get();
        FullPortfolio full = new FullPortfolio(
            id,
            port.getName(),
            port.getUser(),
            port.isSubmitted(),
            port.isApproved(),
            port.isReviewed(),
            port.getFeedback(),
            port.getFlags(),
            aboutMeRepo.findByPortfolioId(id).get(),
            certificationRepo.findAllByPortfolioId(id),
            educationRepo.findAllByPortfolioId(id),
            equivalencyRepo.findAllByPortfolioId(id),
            gitHubRepo.findByPortfolio(port),
            honorRepo.findByPortfolio(port),
            projectRepo.findByPortfolio_Id(id),
            workExperienceRepo.findByPortfolio_Id(id),
            workHistoryRepo.findByPortfolio(port),
            insertSkills(matrixRepo.findAllByPortfolio(port))
        );

        ObjectMapper mapper = new ObjectMapper();
        mapper.addMixIn(FullPortfolio.class, FullPortfolioIgnoreMixin.class);
        mapper.addMixIn(AboutMe.class, PortfolioIgnoreMixin.class);
        mapper.addMixIn(Certification.class, PortfolioIgnoreMixin.class);
        mapper.addMixIn(Education.class, PortfolioIgnoreMixin.class);
        mapper.addMixIn(Equivalency.class, PortfolioIgnoreMixin.class);
        mapper.addMixIn(GitHub.class, PortfolioIgnoreMixin.class);
        mapper.addMixIn(Honor.class, PortfolioIgnoreMixin.class);
        mapper.addMixIn(Project.class, PortfolioIgnoreMixin.class);
        mapper.addMixIn(WorkExperience.class, PortfolioIgnoreMixin.class);
        mapper.addMixIn(WorkHistory.class, PortfolioIgnoreMixin.class);
        mapper.addMixIn(Matrix.class, PortfolioIgnoreMixin.class);

        response.setHeader("Content-Disposition", "attachment; filename=Portfolio-" + id + ".json");
        return new ResponseEntity<>(new ByteArrayResource(mapper.writeValueAsString(full).getBytes()), HttpStatus.OK);
    }

    @Transactional
    @PostMapping(value = "/full", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void postFullPortfolio(@RequestBody FullPortfolio fullPortfolio){

        Portfolio pf = new Portfolio();
        pf.setName(fullPortfolio.getName());
        pf.setUser(fullPortfolio.getUser());
        pf.setSubmitted(fullPortfolio.isSubmitted());
        pf.setApproved(fullPortfolio.isApproved());
        pf.setReviewed(fullPortfolio.isReviewed());
        pf.setFeedback(fullPortfolio.getFeedback());

        int pfid = portRepo.save(pf).getId();
        pf.setId(pfid);

        AboutMe newMe = new AboutMe();
        newMe.setPortfolio(pf);
        newMe.setBio(fullPortfolio.getAboutMe().getBio());
        newMe.setEmail(fullPortfolio.getAboutMe().getEmail());
        newMe.setPhone(fullPortfolio.getAboutMe().getPhone());

        List<Certification> certs = fullPortfolio.getCertifications();
        certs.forEach(cert -> cert.setPortfolio(pf));

        List<Education> ed = fullPortfolio.getEducations();
        ed.forEach(e -> e.setPortfolio(pf));

        List<Equivalency> equivs = fullPortfolio.getEquivalencies();
        equivs.forEach(e -> e.setPortfolio(pf));

        List<GitHub> git = fullPortfolio.getGitHubs();

        List<Honor> honors = fullPortfolio.getHonors();
        honors.forEach(honor -> honor.setPortfolio(pf));

        List<Project> projects = fullPortfolio.getProjects();
        projects.forEach(project -> project.setPortfolio(pf));

        List<WorkExperience> workExp = fullPortfolio.getWorkExperiences();
        workExp.forEach(exp -> exp.setPortfolio(pf));

        List<WorkHistory> workHist = fullPortfolio.getWorkHistories();
        workHist.forEach(hist -> hist.setPortfolio(pf));
         	
    	  List<Matrix> matrices = fullPortfolio.getMatrices();
    	  matrices.forEach(mat -> mat.setPortfolio(pf));

        aboutMeRepo.save(newMe);
        certificationRepo.saveAll(certs);
        educationRepo.saveAll(ed);
        equivalencyRepo.saveAll(equivs);
        gitHubRepo.saveAll(git);
        honorRepo.saveAll(honors);
        projectRepo.saveAll(projects);
        workExperienceRepo.saveAll(workExp);
        workHistoryRepo.saveAll(workHist);
        matrixRepo.saveAll(matrices);
    	  skillRepo.saveAll(extractSkills(matrices));

    }
	
	/**
	 * 
	 * @param max is the list of matrices to be serialized
	 * @return the list with each matrix having its list of skills inserted
	 */
	private List<Matrix> insertSkills(List<Matrix> max) {
		for (Matrix m : max) {
			List<Skill> skills = skillRepo.findAllByMatrix(m);
			m.setSkills(skills);
		}
		return max;
	}

	/**
	 * 
	 * @param listMax is the list of deserialized matrices
	 * @return the list of all the matrices' skills with each Matrix field set for SQL storage
	 */
	private List<Skill> extractSkills(List<Matrix> listMax) {
		List<Skill> allSkills = new ArrayList<>();
		for(Matrix m : listMax) {
			for(Skill s : m.getSkills()) {
				s.setMatrix(m);
				allSkills.add(s);
			}
		}
		return allSkills;
	}
}