package br.com.serratec.beestock.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.serratec.beestock.model.Photo;

public interface PhotoRepository extends JpaRepository<Photo,Integer> {
    
}
