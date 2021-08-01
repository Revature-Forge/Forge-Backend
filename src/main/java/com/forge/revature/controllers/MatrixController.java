package com.forge.revature.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.forge.revature.exception.NotFoundException;
import com.forge.revature.models.Matrix;
import com.forge.revature.models.MatrixDTO;
import com.forge.revature.models.Portfolio;
import com.forge.revature.models.Skill;
import com.forge.revature.models.SkillDTO;
import com.forge.revature.repo.MatrixRepo;
import com.forge.revature.repo.PortfolioRepo;
import com.forge.revature.repo.SkillRepo;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("api/matrix")
public class MatrixController {

	MatrixRepo matrixRepo;

	SkillRepo skillRepo;

	PortfolioRepo portRepo;

	@Autowired
	public MatrixController(MatrixRepo matrixRepo, SkillRepo skillRepo, PortfolioRepo portRepo) {
		this.matrixRepo = matrixRepo;
		this.skillRepo = skillRepo;
		this.portRepo = portRepo;
	}

	/**
	 * 
	 * @return a list of all matrices in the DB
	 */
	@GetMapping
	public List<Matrix> getAll() {
		return insertSkills(matrixRepo.findAll());
	}
	/**
	 * 
	 * @param id of the matrix
	 * @return the matrix with it
	 */
	@GetMapping("/{id}")
	public Matrix getById(@PathVariable("id") int id) {
		Matrix m = matrixRepo.findById(id).orElseThrow(() -> new NotFoundException("Matrix Not Found for ID: " + id));
		return insertSkills(m);
	}
	
	/**
	 * 
	 * @param id of the portfolio to fetch by
	 * @return list of all matrices tied to the portfolio
	 */
	@GetMapping("/portfolio/{id}")
	public List<Matrix> getByPortfolio(@PathVariable("id") int id) {
		Portfolio port = portRepo.findById(id)
				.orElseThrow(() -> new NotFoundException("Portfolio Not Found for ID: " + id));
		return insertSkills(matrixRepo.findAllByPortfolio(port));
	}
	
	/**
	 * 
	 * @param matrix that is a new matrix
	 * @return matrix with it's updated id number
	 */
	@PostMapping
	public Matrix postMatrix(@RequestBody MatrixDTO matrixDTO) {
		Matrix matrix = new Matrix(matrixDTO.getHeader(), matrixDTO.getSkills(), matrixDTO.getPortfolio());
		List<Skill> skills = extractSkills(matrix);
		matrix = matrixRepo.save(matrix);
		if(!skills.isEmpty()) {
			skillRepo.saveAll(skills);
		}
		return matrix;
	}
	
	/**
	 * 
	 * @param id of already existing matrix
	 * @param matrix with changes to update
	 * @return the newly changed matrix
	 */
	@PutMapping
	public Matrix putMatrix(@RequestBody MatrixDTO matrixDTO) {
		Matrix matrix = new Matrix(matrixDTO.getId(), matrixDTO.getHeader(), matrixDTO.getPortfolio(), matrixDTO.getSkills());
		Optional<Matrix> update = matrixRepo.findById(matrix.getId());
		if (update.isPresent()) {
			Matrix newMat = update.get();
			newMat.setHeader(matrix.getHeader());
			newMat.setPortfolio(matrix.getPortfolio());
			matrixRepo.saveAndFlush(newMat);
			List<Skill> oldSkills = skillRepo.findAllByMatrix(newMat);
			if (!oldSkills.isEmpty()) {
				skillRepo.deleteAll(oldSkills);
			}
		} else {
			matrix = matrixRepo.save(matrix);
		}
		List<Skill> newSkills = extractSkills(matrix);
		if (!newSkills.isEmpty()) {
			skillRepo.saveAll(newSkills);
		}
		return matrix;
	}
	
	/**
	 * 
	 * @param id of the matrix to be deleted
	 */
	@DeleteMapping("/{id}")
	public void delete(@PathVariable("id") int id) {
		Optional<Matrix> m = matrixRepo.findById(id);
		if (m.isPresent()) {
			List<Skill> skills = skillRepo.findAllByMatrix(m.get());
			if (!skills.isEmpty()) {
				skillRepo.deleteAll(skills);
			}
			matrixRepo.delete(m.get());
		}
	}
	
	/**
	 * 
	 * @param id of the matrix
	 * @param skill to be inserted or updated
	 * @return changed matrix with skills inside
	 */
	@PutMapping("{id}/skill")
	public Matrix putSkill(@PathVariable("id") int id, @RequestBody SkillDTO skillDTO) {
		Matrix m = matrixRepo.findById(id).orElseThrow(() -> new NotFoundException("Matrix Not Found for ID: " + id));
		if(skillDTO.getId() == 0) {
			skillRepo.saveAndFlush(new Skill(skillDTO.getName(), skillDTO.getValue(), m));
			return insertSkills(m);
		} else {
			Skill skill = skillRepo.findById(skillDTO.getId()).orElseThrow(() -> new NotFoundException("Skill Not Found for ID: " + skillDTO.getId()));
			skill.setName(skillDTO.getName());
			skill.setValue(skillDTO.getValue());
			skillRepo.saveAndFlush(skill);
			return insertSkills(m);
		}
	}
	
	/**
	 * 
	 * @param id of the skill to be deleted
	 */
	@DeleteMapping("/skill/{id}")
	public Matrix deleteSkill(@PathVariable("id") int id) {
		Skill s = skillRepo.findById(id).orElseThrow(() -> new NotFoundException("Skill Not Found for ID: " + id));
		Matrix m = s.getMatrix();
		skillRepo.delete(s);
		return insertSkills(m);
	}
	
	/**
	 * 
	 * @param m is the matrix to insert the skills into for serialization
	 * @return the matrix with it's list of skills inside
	 */
	private Matrix insertSkills(Matrix m) {
		List<Skill> skills = skillRepo.findAllByMatrix(m);
		m.setSkills(skills);
		return m;
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
	 * @param m is the deserialized matrix
	 * @return the list of the skills with each Matrix field set for SQL storage
	 */
	private List<Skill> extractSkills(Matrix m) {
		List<Skill> skills = m.getSkills();
		for(Skill s : skills) {
			s.setMatrix(m);
		}
		return skills;
	}

}
