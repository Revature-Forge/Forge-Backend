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
import com.forge.revature.models.Skill;
import com.forge.revature.repo.MatrixRepo;
import com.forge.revature.repo.SkillRepo;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/skill")
public class SkillController {
	
	private SkillRepo skillRepo;
	
	private MatrixRepo matrixRepo;
	
	@Autowired
	public SkillController(MatrixRepo matrixRepo, SkillRepo skillRepo) {
		this.matrixRepo = matrixRepo;
		this.skillRepo = skillRepo;
	}
	
	@GetMapping
	public List<Skill> getAll() {
		return skillRepo.findAll();
	}
	
	@GetMapping("/{id}")
	public Skill getById(@PathVariable("id") int id) {
		return skillRepo.findById(id).orElseThrow(() -> new NotFoundException("No Skill found with id " + id));
	}
	
	@GetMapping("/matrix/{id}")
	public List<Skill> getByMatrixId(@PathVariable("id") int id) {
		Matrix max = matrixRepo.findById(id).orElseThrow(() -> new NotFoundException("No Matrix found with id " + id));
		return skillRepo.findAllByMatrix(max);
	}
	
	@PostMapping
	public Skill postSkill(@RequestBody Skill skill) {
		return skillRepo.save(skill);
	}
	
	@PostMapping("/{id}")
	public Skill updateSkill(@PathVariable(name = "id") int id, @RequestBody Skill skill) {
		  Optional<Skill> update = skillRepo.findById(id);
	        if (update.isPresent()) {
	            update.get().setName(skill.getName());
	            update.get().setValue(skill.getValue());
	            update.get().setMatrix(skill.getMatrix());
	            skill = update.get();
	        }
		return skillRepo.save(skill);
	}
	
	@DeleteMapping("/{id}")
	public void deleteSkill(@PathVariable("id") int id) {
		skillRepo.deleteById(id);
	}

}
