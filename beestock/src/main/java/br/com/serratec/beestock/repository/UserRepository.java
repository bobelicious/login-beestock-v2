package br.com.serratec.beestock.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.serratec.beestock.model.UserModel;

public interface UserRepository extends JpaRepository<UserModel,Integer> {
    public Optional<UserModel> findByName(String name);
    public Optional<UserModel> findByCpf(String cpf);
    public Optional<UserModel> findByEmail (String email);
}
