package br.com.serratec.beestock.service;

import java.util.*;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Service;

import br.com.serratec.beestock.dto.OrderProductDTO;
import br.com.serratec.beestock.model.OrderProduct;
import br.com.serratec.beestock.repository.OrderProductRepository;

@Service
public class OrderProductService {
    @Autowired
    private OrderProductRepository orderProductRepository;

    @Transactional
    public List<OrderProductDTO> listAll() {
        List<OrderProduct> orderProducts = orderProductRepository.findAll();
        List<OrderProductDTO> orderProductDTOs = new ArrayList<>();
        orderProducts.forEach((o) -> {
            orderProductDTOs.add(new OrderProductDTO(o));
        });

        return orderProductDTOs;
    }
    @Transactional
    public void addOrderProduct(OrderProduct orderProduct){
        orderProductRepository.save(orderProduct);
    }
}
