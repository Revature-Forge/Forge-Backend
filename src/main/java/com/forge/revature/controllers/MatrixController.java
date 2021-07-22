package com.forge.revature.controllers;

import java.util.ArrayList;
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

import com.forge.revature.models.Matrix;
import com.forge.revature.models.Skill;
import com.forge.revature.repo.MatrixRepo;
import com.forge.revature.repo.SkillRepo;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/matrix")
public class MatrixController {

	@Autowired
	MatrixRepo matrixRepo;

	@Autowired
	SkillRepo skillRepo;

	@GetMapping
	public List<Matrix> getAll() {
		return insertSkills(matrixRepo.findAll());
	}

	@GetMapping("/{id}")
	public Matrix getById(@PathVariable("id") int id) {
		return insertSkills(matrixRepo.findById(id));
	}

	@GetMapping("/portfolio/{id}")
	public List<Matrix> getByPortfolio(@PathVariable("id") int id) {
		return insertSkills(matrixRepo.findAllByPortfolioId(id));
	}

	@PostMapping
	public Matrix postMatrix(@RequestBody Matrix matrix) {
		List<Skill> skills = extractSkills(matrix);
		matrix = matrixRepo.save(matrix);
		skillRepo.saveAll(skills);
		return matrix;
	}

	@PutMapping
	public Matrix putMatrix(@RequestBody Matrix matrix) {
		List<Skill> newSkills = extractSkills(matrix);
		List<Skill> oldSkills = skillRepo.findAllByMatrix(matrix);
		matrix = matrixRepo.save(matrix);
		if (!oldSkills.isEmpty()) {
			skillRepo.deleteAll(oldSkills);
		}
		if (!newSkills.isEmpty()) {
			skillRepo.saveAll(newSkills);
		}
		return matrix;
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

	private Matrix insertSkills(Optional<Matrix> m) {
		Matrix max = new Matrix();
		if (m.isPresent()) {
			max = m.get();
		} else {
			return max;
		}
		max.setSkills(skillRepo.findAllByMatrix(max));
		return max;
	}

	private List<Matrix> insertSkills(List<Matrix> matrices) {
		for (Matrix m : matrices) {
			m.setSkills(skillRepo.findAllByMatrix(m));
		}
		return matrices;
	}

	private List<Skill> extractSkills(Matrix max) {
		List<Skill> skills = new ArrayList<>();
		for (Skill s : max.getSkills()) {
			s.setMatrix(max);
			skills.add(s);
		}
		return skills;
	}

	private List<Skill> extractSkills(List<Matrix> matrices) {
		List<Skill> skills = new ArrayList<>();
		for (Matrix m : matrices) {
			for (Skill s : m.getSkills()) {
				s.setMatrix(m);
				skills.add(s);
			}
		}
		return skills;
	}

}
