package br.com.serratec.beestock.controller;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import br.com.serratec.beestock.exceptions.EmailException;
import br.com.serratec.beestock.exceptions.NotFindException;
import br.com.serratec.beestock.model.Photo;
import br.com.serratec.beestock.service.PhotoService;

@RestController
@RequestMapping("/fotos")
public class PhotoController {
    @Autowired
    private PhotoService photoService;

    @GetMapping("/{id}")
    public ResponseEntity<byte[]> findPhoto(@PathVariable Integer id) throws NotFindException {
        Photo photo = photoService.buscar(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("content-type", photo.getType());
        headers.add("content-length", String.valueOf(photo.getData().length));
        return new ResponseEntity<>(photo.getData(), headers, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<?> postPhoto(@RequestBody MultipartFile file){
        Photo photo;
        try {
            photo = photoService.inserir(file);
            HttpHeaders headers = new HttpHeaders();
            headers.add("content-type", photo.getType());
            headers.add("content-length", String.valueOf(photo.getData().length));
            return new ResponseEntity<>(photo.getData(), headers, HttpStatus.CREATED);
        } catch (IOException | EmailException e) {
            return ResponseEntity.unprocessableEntity().body(e.getMessage());
        }
    }
}
