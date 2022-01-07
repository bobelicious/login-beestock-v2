package br.com.serratec.beestock.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import br.com.serratec.beestock.dto.OrderProductDTO;
import br.com.serratec.beestock.service.OrderProductService;

@RequestMapping("/pedidoItens")
@RestController
public class OrderProductController {
    @Autowired
    private OrderProductService orderProductService;

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @GetMapping
    public ResponseEntity<List<OrderProductDTO>> listAll(){
        return ResponseEntity.ok().body(orderProductService.listAll());
    }
}
