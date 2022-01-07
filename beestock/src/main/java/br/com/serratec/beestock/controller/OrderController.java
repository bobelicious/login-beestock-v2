package br.com.serratec.beestock.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.serratec.beestock.dto.OrderDTO;
import br.com.serratec.beestock.exceptions.CompletedOrderException;
import br.com.serratec.beestock.exceptions.InsufficientQuantityException;
import br.com.serratec.beestock.exceptions.NotFindException;
import br.com.serratec.beestock.model.OrderModel;
import br.com.serratec.beestock.model.OrderProduct;
import br.com.serratec.beestock.service.OrderService;

@RestController
@RequestMapping("/pedidos")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @GetMapping
    public List<OrderDTO> listAll(){
        return orderService.listAll();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
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

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @PutMapping("/finalizar/{id}")
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

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @PutMapping("/alterar/{id}")
    public ResponseEntity<?> attOrder(@PathVariable Integer id, @RequestBody List<OrderProduct> orderProducts) {
        OrderDTO orderDTO;
        try {
            orderDTO = orderService.editOrder(orderProducts, id);
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(orderDTO.getId())
            .toUri();
            return ResponseEntity.created(uri).body(orderDTO);
        } catch (CompletedOrderException | NotFindException e) {
            return ResponseEntity.unprocessableEntity().body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @DeleteMapping("cancelar/{id}")
    public ResponseEntity<?> cancelOrder(@PathVariable Integer id){
        try {
            orderService.cancelOrder(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.unprocessableEntity().body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @PutMapping("/devolver/{id}")
    public ResponseEntity<?> giveBackOrder(@PathVariable Integer id,@RequestBody OrderModel order){
        try {
            orderService.giveBackOrder(id, order);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.unprocessableEntity().body(e.getMessage());
        }
    }
}
