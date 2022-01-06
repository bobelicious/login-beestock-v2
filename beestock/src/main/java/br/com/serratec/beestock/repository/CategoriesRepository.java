package br.com.serratec.beestock.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.serratec.beestock.model.Categories;

public interface CategoriesRepository extends JpaRepository<Categories,Integer> {
    Optional<Categories> findByName(String name);
}
