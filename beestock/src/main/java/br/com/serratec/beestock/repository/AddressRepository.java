package br.com.serratec.beestock.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.serratec.beestock.model.Address;

public interface AddressRepository extends JpaRepository<Address,Integer> {
    public Address findByCep(String cep);
}
