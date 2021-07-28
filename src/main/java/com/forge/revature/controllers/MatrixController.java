package com.forge.revature.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.forge.revature.exception.NotFoundException;
import com.forge.revature.models.Matrix;
import com.forge.revature.models.Portfolio;
import com.forge.revature.models.Skill;
import com.forge.revature.repo.MatrixRepo;
import com.forge.revature.repo.PortfolioRepo;
import com.forge.revature.repo.SkillRepo;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/matrix")
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

	@GetMapping
	public List<Matrix> getAll() {
		return insertSkills(matrixRepo.findAll());
	}

	@GetMapping("/{id}")
	public Matrix getById(@PathVariable("id") int id) {
		Matrix m = matrixRepo.findById(id).orElseThrow(() -> new NotFoundException("Matrix Not Found for ID: " + id));
		return insertSkills(m);
	}
	
	/**
	 * 
	 * @param id of the portfolio
	 * @return list of all matrices tied to the portfolio
	 */
	@GetMapping("/portfolio/{id}")
	public List<Matrix> getByPortfolio(@PathVariable("id") int id) {
		Portfolio port = portRepo.findById(id)
				.orElseThrow(() -> new NotFoundException("Portfolio Not Found for ID: " + id));
		List<Matrix> max = insertSkills(matrixRepo.findAllByPortfolio(port));
		return max;
	}

	@PostMapping
	public Matrix postMatrix(@RequestBody Matrix matrix) {
		return extractSkills(matrixRepo.save(matrix));
	}

	@PostMapping("/{id}")
	public Matrix putMatrix(@PathVariable("id") int id, @RequestBody Matrix matrix) {
		Optional<Matrix> update = matrixRepo.findById(id);
		if (update.isPresent()) {
			update.get().setHeader(matrix.getHeader());
			update.get().setPortfolio(matrix.getPortfolio());
			matrix = update.get();
		}
		return extractSkills(matrixRepo.save(matrix));
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable("id") int id) {
		Optional<Matrix> m = matrixRepo.findById(id);
		Matrix max = new Matrix();
		if (m.isPresent()) {
			max = m.get();
		} else {
			return;
		}
		List<Skill> skills = skillRepo.findAllByMatrix(max);
		if (!skills.isEmpty()) {
			skillRepo.deleteAll(skills);
		}
		matrixRepo.delete(max);
	}

	private Matrix insertSkills(Matrix m) {
		List<Skill> skills = skillRepo.findAllByMatrix(m);
		m.setSkills(skills);
		return m;
	}

	private List<Matrix> insertSkills(List<Matrix> max) {
		for (Matrix m : max) {
			List<Skill> skills = skillRepo.findAllByMatrix(m);
			m.setSkills(skills);
		}
		return max;
	}

	private Matrix extractSkills(Matrix m) {
		List<Skill> oldSkills = skillRepo.findAllByMatrix(m);
		List<Skill> newSkills = m.getSkills();
		for(Skill s : newSkills) {
			s.setMatrix(m);
		}
		if (!oldSkills.isEmpty()) {
			skillRepo.deleteAll(oldSkills);
		}
		if (!newSkills.isEmpty()) {
			skillRepo.saveAll(newSkills);
		}
		return m;
	}

//	private List<Matrix> extractSkills(List<Matrix> max) {
//		for (Matrix m : max) {
//			List<Skill> oldSkills = skillRepo.findAllByMatrix(m);
//			List<Skill> newSkills = m.getSkills();
//			if (!oldSkills.isEmpty()) {
//				skillRepo.deleteAll(oldSkills);
//			}
//			if (!newSkills.isEmpty()) {
//				skillRepo.saveAll(newSkills);
//			}
//		}
//		return max;
//	}

}
