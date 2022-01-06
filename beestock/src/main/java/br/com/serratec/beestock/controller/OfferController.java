package br.com.serratec.beestock.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.serratec.beestock.model.Offer;
import br.com.serratec.beestock.service.OfferService;

@RestController
@RequestMapping("/ofertas")
public class OfferController {
    @Autowired
    private OfferService saleService;

    @GetMapping
    public List<Offer> listAll(){
        return saleService.listAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Offer>> findById(@PathVariable Integer id){
        try {
            return ResponseEntity.ok().body(saleService.findById(id));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<?> addSale(@RequestBody Offer sale){
        try {
            Offer s = saleService.addSale(sale);
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(s.getId())
            .toUri();
            return ResponseEntity.created(uri).body(s);
        } catch (Exception e) {
            return ResponseEntity.unprocessableEntity().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> attSale(@PathVariable Integer id, @RequestBody Offer sale){
        try {
            Offer s = saleService.attSale(id, sale);
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(s.getId())
            .toUri();
            return ResponseEntity.created(uri).body(s);
        } catch (Exception e) {
            return ResponseEntity.unprocessableEntity().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delteSale(@PathVariable Integer id){
        try {
            saleService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.unprocessableEntity().body(e.getMessage());
        }
    }
}
