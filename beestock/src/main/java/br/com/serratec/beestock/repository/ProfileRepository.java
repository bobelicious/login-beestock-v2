package br.com.serratec.beestock.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.serratec.beestock.model.Profile;

public interface ProfileRepository extends JpaRepository<Profile, Integer> {
    
}

