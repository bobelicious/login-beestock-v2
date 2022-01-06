package br.com.serratec.beestock.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.serratec.beestock.dto.OrderDTO;
import br.com.serratec.beestock.exceptions.InsufficientQuantityException;
import br.com.serratec.beestock.exceptions.NotFindException;
import br.com.serratec.beestock.model.OrderProduct;
import br.com.serratec.beestock.service.OrderService;

@RestController
@RequestMapping("/pedidos")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping
    public List<OrderDTO> listAll(){
        return orderService.listAll();
    }

    @PostMapping("/fazerpedido")
    public ResponseEntity<?> addOrder(@RequestBody List<OrderProduct> orderProducts) throws NotFindException{
        try {
            OrderDTO orderDTO = orderService.addOrder(orderProducts);
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(orderDTO.getId())
            .toUri();
            return ResponseEntity.created(uri).body(orderDTO);
        } catch (Exception e) {
            return ResponseEntity.unprocessableEntity().body(e.getMessage());
        }
    }

    @PutMapping("/finalizarpedido/{id}")
    public ResponseEntity<?> finalizeOrder(@PathVariable Integer id){
            OrderDTO orderDTO;
            try {
                orderDTO = orderService.finalizeOrder(id);
                URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(orderDTO.getId())
                .toUri();
                return ResponseEntity.created(uri).body(orderDTO);
            } catch (NotFindException | InsufficientQuantityException e) {
                return ResponseEntity.unprocessableEntity().body(e.getMessage());
            }

    }
}
