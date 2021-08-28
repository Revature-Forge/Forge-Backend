package com.forge.revature.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import javax.servlet.http.HttpServletResponse;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.forge.revature.controllers.FullPortfolioIgnoreMixin;
import com.forge.revature.controllers.PortfolioIgnoreMixin;
import com.forge.revature.models.AboutMe;
import com.forge.revature.models.Certification;
import com.forge.revature.models.Education;
import com.forge.revature.models.Equivalency;
import com.forge.revature.models.FullPortfolio;
import com.forge.revature.models.GitHub;
import com.forge.revature.models.Honor;
import com.forge.revature.models.Matrix;
import com.forge.revature.models.Portfolio;
import com.forge.revature.models.Project;
import com.forge.revature.models.Skill;
import com.forge.revature.models.WorkExperience;
import com.forge.revature.models.WorkHistory;
import com.forge.revature.repo.AboutMeRepo;
import com.forge.revature.repo.CertificationRepo;
import com.forge.revature.repo.EducationRepo;
import com.forge.revature.repo.EquivalencyRepo;
import com.forge.revature.repo.GitHubRepo;
import com.forge.revature.repo.HonorRepo;
import com.forge.revature.repo.MatrixRepo;
import com.forge.revature.repo.PortfolioRepo;
import com.forge.revature.repo.ProjectRepo;
import com.forge.revature.repo.SkillRepo;
import com.forge.revature.repo.WorkExperienceRepo;
import com.forge.revature.repo.WorkHistoryRepo;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PortfolioService {
	
	PortfolioRepo portRepo;
	AboutMeRepo aboutMeRepo;
	CertificationRepo certificationRepo;
	EducationRepo educationRepo;
	EquivalencyRepo equivalencyRepo;
	GitHubRepo gitHubRepo;
	HonorRepo honorRepo;
	ProjectRepo projectRepo;
	WorkExperienceRepo workExperienceRepo;
	WorkHistoryRepo workHistoryRepo;
	MatrixRepo matrixRepo;
	SkillRepo skillRepo;
	
	
	public List<Portfolio> getAll(){
		List<Portfolio> ports = StreamSupport.stream(portRepo.findAll().spliterator(), false)
		.collect(Collectors.toList());
	return ports;
	}
	
	
	public Portfolio getByID(int id){
		return portRepo.findById(id).get();
	}
	
	
	public List<Portfolio> getPortfoliosByUserId(int id){
		List<Portfolio> portfolios = portRepo.findAllByUserId(id);
		return portfolios;
	}
	
	
	public Portfolio postPort(Portfolio port){
		return portRepo.save(port);
	}
	
	public void updateUser(int id , Portfolio updated){
		Optional<Portfolio> old = portRepo.findById(id);
	
		if(old.isPresent()){
			old.get().setApproved(updated.isApproved());
			old.get().setFeedback(updated.getFeedback());
			old.get().setName(updated.getName());
			old.get().setReviewed(updated.isReviewed());
			old.get().setSubmitted(updated.isSubmitted());
			old.get().setFlags(updated.getFlags());
			portRepo.save(old.get());
		}
	}
	
	
	public Map<String, Boolean> deletePortfolio(int id) throws ResourceNotFoundException{
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
	
	
	public ResponseEntity<ByteArrayResource> getFullPortfolio(int id, HttpServletResponse response) throws JsonProcessingException {
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
	
	
	public void postFullPortfolio(FullPortfolio fullPortfolio){
	
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
	
	
	public List<Matrix> insertSkills(List<Matrix> max) {
		for (Matrix m : max) {
			List<Skill> skills = skillRepo.findAllByMatrix(m);
			m.setSkills(skills);
		}
		return max;
	}
	
	
	public List<Skill> extractSkills(List<Matrix> listMax) {
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
