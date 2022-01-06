package br.com.serratec.beestock.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.serratec.beestock.model.OrderModel;
import br.com.serratec.beestock.model.UserModel;

public interface OrderRepository extends JpaRepository<OrderModel,Integer>  {
    public Optional<OrderModel> findByUser(UserModel user);
}
