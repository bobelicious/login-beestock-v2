package br.com.serratec.beestock.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.serratec.beestock.model.Product;

public interface ProductRepository extends JpaRepository<Product,Integer> {
    public Optional<Product>findByCodeSKU(String codeSKU);
}
