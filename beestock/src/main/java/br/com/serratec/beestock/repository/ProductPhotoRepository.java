package br.com.serratec.beestock.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.serratec.beestock.model.ProductPhoto;

public interface ProductPhotoRepository extends JpaRepository<ProductPhoto,Integer> {
    
}
