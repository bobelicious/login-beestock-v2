package br.com.serratec.beestock.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.serratec.beestock.model.Offer;

public interface OfferRepository extends JpaRepository<Offer,Integer>{
    
}
    

