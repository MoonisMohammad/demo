package com.example.demo.facility;

import com.example.demo.appuser.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Objects;

@Service
public class FacilityService {

    private final FacilityRepository facilityRepository;

    @Autowired
    public FacilityService(FacilityRepository facilityRepository) {
        this.facilityRepository = facilityRepository;
    }

    public List<Facility> getFacilities(){
        return facilityRepository.findAll();
    }

    public Facility getFacilityWithId(Long facilityId) {
        Optional<Facility> facilityOptional = facilityRepository.findById(facilityId);
        if(!facilityOptional.isPresent()) {
            throw new IllegalStateException(
                    "facility with id "+facilityId+"does not exists");
        }

        return facilityOptional.get();
    }

    public List<Facility> getOwnedFacilities(){
        AppUser appUser = getCurrentUser();
        return facilityRepository.findByOwnerId(appUser.getId());
    }

    public void addNewFacility(Facility facility){
        AppUser appUser = getCurrentUser();
        facility.setOwnerId(appUser.getId());
        facilityRepository.save(facility);
    }

    public void deleteFacility(Long facilityId){
        AppUser appUser = getCurrentUser();
        Optional<Facility> facilityOptional = facilityRepository.findById(facilityId);
        if(!facilityOptional.isPresent()) {
            throw new IllegalStateException(
                    "facility with id "+facilityId+"does not exists");
        }
        Long facilityOwnerId = facilityOptional.get().getOwnerId();
        if(appUser.getId().equals(facilityOwnerId)) {
            facilityRepository.deleteById(facilityId);
        }else{
            throw new IllegalStateException(
                    "facility with id "+facilityId+"not owned by user");
        }
    }

    @Transactional
    public void updateFacility(Long facilityId,
                              String name,
                              String location) {
        AppUser appUser = getCurrentUser();

        Facility facility = facilityRepository.findById(facilityId)
                .orElseThrow(() -> new IllegalStateException(
                        "student with id " + facilityId + "does not exist"));
        Long facilityOwnerId = facility.getOwnerId();
        if(!appUser.getId().equals(facilityOwnerId)){
            throw new IllegalStateException(
                    "facility with id "+facilityId+"not owned by user");
        }

        if(name != null && name.length()>0 && !Objects.equals(facility.getName(),name)) {
            facility.setName(name);
        }
        if(location !=null && location.length()>0&&!Objects.equals(facility.getLocation(),location)) {
            facility.setLocation(location);
        }
    }

    public AppUser getCurrentUser(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        AppUser appUser;
        if (principal instanceof AppUser) {
            appUser = ((AppUser)principal);
            return appUser;

        }else {
            return null;

        }
    }


}
