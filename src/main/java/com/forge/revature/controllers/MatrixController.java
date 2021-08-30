package com.forge.revature.controllers;

import java.util.List;
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
import com.forge.revature.models.MatrixDTO;
import com.forge.revature.models.SkillDTO;
import com.forge.revature.services.MatrixService;
import lombok.AllArgsConstructor;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("api/matrix")
@AllArgsConstructor
public class MatrixController {
	
	private MatrixService matrixService;
	/**
	* 
	* @return a list of all matrices in the DB
	*/
	@GetMapping
	public List<Matrix> getAll() {
		return matrixService.getAll();
	}
	
	/**
	* 
	* @param id of the matrix
	* @return the matrix with it
	*/
	@GetMapping("/{id}")
	public Matrix getById(@PathVariable("id") int id) {
		return matrixService.getById(id);
	}
	
	/**
	* 
	* @param id of the portfolio to fetch by
	* @return list of all matrices tied to the portfolio
	*/
	@GetMapping("/portfolio/{id}")
	public List<Matrix> getByPortfolio(@PathVariable("id") int id) {
		return matrixService.getByPortfolio(id);
	}
	
	/**
	* 
	* @param matrix that is a new matrix
	* @return matrix with it's updated id number
	*/
	@PostMapping
	public Matrix postMatrix(@RequestBody MatrixDTO matrixDTO) {
		return matrixService.postMatrix(matrixDTO);
	}
	
	/**
	* 
	* @param id     of already existing matrix
	* @param matrix with changes to update
	* @return the newly changed matrix
	*/
	@PutMapping
	public Matrix putMatrix(@RequestBody MatrixDTO matrixDTO) {
		return matrixService.putMatrix(matrixDTO);
	}
	
	/**
	* 
	* @param id of the matrix to be deleted
	*/
	@DeleteMapping("/{id}")
	public void delete(@PathVariable("id") int id) {
		matrixService.delete(id);
	}
	
	/**
	* 
	* @param id    of the matrix
	* @param skill to be inserted or updated
	* @return changed matrix with skills inside
	*/
	@PutMapping("/{id}/skill")
	public Matrix putSkill(@PathVariable("id") int id, @RequestBody SkillDTO skillDTO) {
		return matrixService.putSkill(id, skillDTO);
	}
	
	/**
	* 
	* @param id of the skill to be deleted
	*/
	@DeleteMapping("/skill/{id}")
	public Matrix deleteSkill(@PathVariable("id") int id) {
		return matrixService.deleteSkill(id);
	}

}
