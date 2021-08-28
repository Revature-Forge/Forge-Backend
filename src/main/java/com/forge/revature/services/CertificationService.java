package com.forge.revature.services;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.HashMap;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import com.forge.revature.models.Certification;
import com.forge.revature.repo.CertificationRepo;
import lombok.AllArgsConstructor;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;



@Service
@AllArgsConstructor
public class CertificationService {

    CertificationRepo certificationRepo;

    public List<Certification> getAll() {
        List<Certification> certifications = StreamSupport.stream(certificationRepo.findAll().spliterator(), false).collect(Collectors.toList());
        return certifications;
    }

    public Certification getCertification(long id) {
        return certificationRepo.findById(id).get();
    }

    public List<Certification> getAllCertificationsByPortfolioId(int id){
        List<Certification> certifications = certificationRepo.findAllByPortfolioId(id);

        return certifications;
    }

    public Certification postCertification(Certification certification) {
        return certificationRepo.save(certification);
    }

    public void updateCertification(Certification newCertification, long id) {
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

    public Map<String, Boolean> deleteCertification(long id) throws ResourceNotFoundException {
        Certification certification = certificationRepo.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Certification not found for this id ::" + id));
        certificationRepo.delete(certification);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}

