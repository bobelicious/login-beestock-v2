package br.com.serratec.beestock.controller;

import java.io.IOException;
import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.serratec.beestock.dto.ProductDTO;
import br.com.serratec.beestock.dto.ProductPostDTO;
import br.com.serratec.beestock.exceptions.NotFindException;
import br.com.serratec.beestock.model.Product;
import br.com.serratec.beestock.model.ProductPhoto;
import br.com.serratec.beestock.service.ProductPhotoService;
import br.com.serratec.beestock.service.ProductService;

@RestController
@RequestMapping("/produtos")
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductPhotoService productPhotoService;

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @GetMapping
    public ResponseEntity<List<ProductDTO>> findAll() {
        return ResponseEntity.ok().body(productService.findAll());
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Integer id) {
        try {
            ProductDTO productDTO = productService.findByID(id);
            return ResponseEntity.ok().body(productDTO);
        } catch (NotFindException e) {
            return ResponseEntity.unprocessableEntity().body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<?> addProduct (@RequestPart Product product, @RequestParam MultipartFile file){
        try {
           ProductPostDTO productDTO = productService.addProduct(product, file);
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(product.getId())
            .toUri();
            return ResponseEntity.created(uri).body(productDTO);
        } catch (IOException e) {
           return ResponseEntity.unprocessableEntity().body(e.getMessage());
        }
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<?> attProduct (@PathVariable Integer id, @RequestPart Product product, @RequestParam MultipartFile file){
        try {
            ProductDTO productDTO = productService.attProduct(product, file, id);
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(product.getId())
            .toUri();
            return ResponseEntity.created(uri).body(productDTO);
        } catch (Exception e) {
            return ResponseEntity.unprocessableEntity().body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct (@PathVariable Integer id){
        try {
            productService.deleteProduct(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.unprocessableEntity().body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @GetMapping("/{id}/foto")
    public ResponseEntity<byte[]> searchPhoto(@PathVariable Integer id){
        ProductPhoto foto = productPhotoService.search(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("content-type", foto.getType());
        headers.add("content-length", String.valueOf(foto.getData().length));
        return new ResponseEntity<>(foto.getData(), headers, HttpStatus.OK);
    }
}
