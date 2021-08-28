package com.forge.revature.controllers;

import java.util.List;
import java.util.Map;
import com.forge.revature.models.Certification;
import com.forge.revature.services.CertificationService;

import lombok.AllArgsConstructor;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
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
@RequestMapping("api/certifications")
@AllArgsConstructor
public class CertificationController {

    private CertificationService certificationService;

    @GetMapping
    public List<Certification> getAll() {
        return certificationService.getAll();
    }

    @GetMapping("/{id}")
    public Certification getCertification(@PathVariable long id) {
    	return certificationService.getCertification(id);
    }

    @GetMapping("portfolio/all/{id}")
    public List<Certification> getAllCertificationsByPortfolioId(@PathVariable int id){
    	return certificationService.getAllCertificationsByPortfolioId(id);
    }

    @PostMapping
    public Certification postCertification(@RequestBody Certification certification) {
    	return certificationService.postCertification(certification);
    }

    @PostMapping("/{id}")
    public void updateCertification(@RequestBody Certification newCertification, @PathVariable long id) {
    	certificationService.updateCertification(newCertification, id);
    }

    @DeleteMapping("/{id}")
    public Map<String, Boolean> deleteCertification(@PathVariable long id) throws ResourceNotFoundException {
    	return certificationService.deleteCertification(id);
    }
}
