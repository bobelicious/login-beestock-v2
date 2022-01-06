package br.com.serratec.beestock.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.serratec.beestock.model.OrderProduct;

public interface OrderProductRepository extends JpaRepository<OrderProduct,Integer> {
    public List <OrderProduct>  findByOrder(Integer id);
}
