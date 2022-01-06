package br.com.serratec.beestock.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.serratec.beestock.model.Shipping;

public interface ShippingRepository extends JpaRepository<Shipping,Integer> {
    
}
