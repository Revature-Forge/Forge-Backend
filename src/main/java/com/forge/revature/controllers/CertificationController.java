package com.forge.revature.controllers;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.HashMap;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.forge.revature.models.Certification;
import com.forge.revature.repo.CertificationRepo;

import org.springframework.beans.factory.annotation.Autowired;
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
public class CertificationController {
    @Autowired
    CertificationRepo certificationRepo;

    @GetMapping
    public List<Certification> getAll() {
        List<Certification> certifications = StreamSupport.stream(certificationRepo.findAll().spliterator(), false).collect(Collectors.toList());
        return certifications;
    }

    @GetMapping("/{id}")
    public Certification getCertification(@PathVariable long id) {
        return certificationRepo.findById(id).get();
    }

    @GetMapping("portfolio/all/{id}")
    public List<Certification> getAllCertificationsByPortfolioId(@PathVariable int id){
        List<Certification> certifications = certificationRepo.findAllByPortfolioId(id);

        return certifications;
    }

    @PostMapping
    public Certification postCertification(@RequestBody Certification certification) {
        return certificationRepo.save(certification);
    }

    @PostMapping("/{id}")
    public void updateCertification(@RequestBody Certification newCertification, @PathVariable long id) {
        Optional<Certification> oldCertification = certificationRepo.findById(id);

        if(oldCertification.isPresent()) {
            oldCertification.get().setName(newCertification.getName());
            oldCertification.get().setIssuedBy(newCertification.getIssuedBy());
            oldCertification.get().setIssuedOn(newCertification.getIssuedOn());
            oldCertification.get().setCertId(newCertification.getCertId());
            oldCertification.get().setPublicUrl(newCertification.getPublicUrl());
        }
        certificationRepo.save(oldCertification.get());
    }

    @DeleteMapping("/{id}")
    public Map<String, Boolean> deleteCertification(@PathVariable long id) throws ResourceNotFoundException {
        Certification certification = certificationRepo.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Certification not found for this id ::" + id));
        certificationRepo.delete(certification);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}
