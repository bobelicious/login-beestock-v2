package br.com.serratec.beestock.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.serratec.beestock.model.UserProfile;
@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile,Integer>{
    
}
