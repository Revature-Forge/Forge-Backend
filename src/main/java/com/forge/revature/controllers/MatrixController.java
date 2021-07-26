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
		return matrixRepo.findAll();
	}

	@GetMapping("/{id}")
	public Matrix getById(@PathVariable("id") int id) {
		return matrixRepo.findById(id).orElseThrow(() -> new NotFoundException("Matrix Not Found for ID: " + id));
	}

	@GetMapping("/portfolio/{id}")
	public List<Matrix> getByPortfolio(@PathVariable("id") int id) {
		Portfolio port = portRepo.findById(id)
				.orElseThrow(() -> new NotFoundException("Portfolio Not Found for ID: " + id));
		List<Matrix> max = matrixRepo.findAllByPortfolio(port);
		return max;
	}

	@PostMapping
	public Matrix postMatrix(@RequestBody Matrix matrix) {
		return matrixRepo.save(matrix);
	}

	@PostMapping("/{id}")
	public Matrix putMatrix(@PathVariable("id") int id, @RequestBody Matrix matrix) {
		Optional<Matrix> update = matrixRepo.findById(id);
		if (update.isPresent()) {
			update.get().setHeader(matrix.getHeader());
			update.get().setPortfolio(matrix.getPortfolio());
			matrix = update.get();
		}
		return matrixRepo.save(matrix);
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

//	private Matrix insertSkills(Matrix max) {
//		max.setSkills(skillRepo.findAllByMatrix(max));
//		return max;
//	}
//
//	private List<Matrix> insertSkills(List<Matrix> matrices) {
//		for (Matrix m : matrices) {
//			m.setSkills(skillRepo.findAllByMatrix(m));
//		}
//		return matrices;
//	}
//
//	private List<Skill> extractSkills(Matrix max) {
//		List<Skill> skills = new ArrayList<>();
//		for (Skill s : max.getSkills()) {
//			s.setMatrix(max);
//			skills.add(s);
//		}
//		return skills;
//	}
//
//	private List<Skill> extractSkills(List<Matrix> matrices) {
//		List<Skill> skills = new ArrayList<>();
//		for (Matrix m : matrices) {
//			for (Skill s : m.getSkills()) {
//				s.setMatrix(m);
//				skills.add(s);
//			}
//		}
//		return skills;
//	}

}
