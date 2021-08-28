package com.forge.revature.services;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.forge.revature.exception.NotFoundException;
import com.forge.revature.models.Matrix;
import com.forge.revature.models.MatrixDTO;
import com.forge.revature.models.Portfolio;
import com.forge.revature.models.Skill;
import com.forge.revature.models.SkillDTO;
import com.forge.revature.repo.MatrixRepo;
import com.forge.revature.repo.PortfolioRepo;
import com.forge.revature.repo.SkillRepo;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MatrixService {

	MatrixRepo matrixRepo;
	SkillRepo skillRepo;
	PortfolioRepo portRepo;

	/**
	 * 
	 * @return a list of all matrices in the DB
	 */
	public List<Matrix> getAll() {
		return insertSkills(matrixRepo.findAll());
	}

	/**
	 * 
	 * @param id of the matrix
	 * @return the matrix with it
	 */
	public Matrix getById(int id) {
		Matrix m = matrixRepo.findById(id).orElseThrow(() -> new NotFoundException("Matrix Not Found for ID: " + id));
		return insertSkills(m);
	}

	/**
	 * 
	 * @param id of the portfolio to fetch by
	 * @return list of all matrices tied to the portfolio
	 */
	public List<Matrix> getByPortfolio(int id) {
		Portfolio port = portRepo.findById(id)
				.orElseThrow(() -> new NotFoundException("Portfolio Not Found for ID: " + id));
		return insertSkills(matrixRepo.findAllByPortfolio(port));
	}

	/**
	 * 
	 * @param matrix that is a new matrix
	 * @return matrix with it's updated id number
	 */
	public Matrix postMatrix(MatrixDTO matrixDTO) {
		Matrix matrix = new Matrix(matrixDTO.getHeader(), matrixDTO.getSkills(), matrixDTO.getPortfolio());
		List<Skill> skills;
		try {
			skills = extractSkills(matrix);
		} catch (NullPointerException e) {
			return matrixRepo.save(matrix);
		}
		matrix = matrixRepo.save(matrix);
		if (!skills.isEmpty()) {
			skillRepo.saveAll(skills);
		}
		return matrix;
	}

	/**
	 * 
	 * @param id     of already existing matrix
	 * @param matrix with changes to update
	 * @return the newly changed matrix
	 */
	public Matrix putMatrix(MatrixDTO matrixDTO) {
		Matrix matrix;
		try {
			matrix = new Matrix(matrixDTO.getId(), matrixDTO.getHeader(), matrixDTO.getPortfolio(),
					matrixDTO.getSkills());
		} catch (NullPointerException e) {
			matrix = new Matrix(matrixDTO.getHeader(), matrixDTO.getPortfolio());
			matrix.setId(matrixDTO.getId());
		}
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
		List<Skill> newSkills;
		try {
			newSkills = extractSkills(matrix);
		} catch (NullPointerException e) {
			return matrix;
		}
		if (!newSkills.isEmpty()) {
			skillRepo.saveAll(newSkills);
		}
		return matrix;
	}

	/**
	 * 
	 * @param id of the matrix to be deleted
	 */
	public void delete(int id) {
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
	 * @param id    of the matrix
	 * @param skill to be inserted or updated
	 * @return changed matrix with skills inside
	 */
	public Matrix putSkill(int id, SkillDTO skillDTO) {
		Matrix m = matrixRepo.findById(id).orElseThrow(() -> new NotFoundException("Matrix Not Found for ID: " + id));
		if (skillDTO.getId() == 0) {
			skillRepo.saveAndFlush(new Skill(skillDTO.getName(), skillDTO.getValue(), m));
			return insertSkills(m);
		} else {
			Skill skill = skillRepo.findById(skillDTO.getId())
					.orElseThrow(() -> new NotFoundException("Skill Not Found for ID: " + skillDTO.getId()));
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
	public Matrix deleteSkill(int id) {
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
		for (Skill s : skills) {
			s.setMatrix(m);
		}
		return skills;
	}

}
