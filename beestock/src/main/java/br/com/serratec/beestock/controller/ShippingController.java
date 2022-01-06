package br.com.serratec.beestock.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.serratec.beestock.model.Shipping;
import br.com.serratec.beestock.service.ShippingService;

@RestController
@RequestMapping("/remessas")
public class ShippingController {
    @Autowired
    private ShippingService shippingService;

    @GetMapping
    public List<Shipping> listAll(){
        return shippingService.listAll();
    }

    @GetMapping("/{id}")
    public Optional<Shipping> findById(@PathVariable Integer id){
        return shippingService.findById(id);
    }

    @PostMapping
    public ResponseEntity<Shipping> addShipping (@RequestBody Shipping shipping){
        shippingService.addShipping(shipping);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(shipping.getId())
        .toUri();
        return ResponseEntity.created(uri).body(shipping);
    }
}
