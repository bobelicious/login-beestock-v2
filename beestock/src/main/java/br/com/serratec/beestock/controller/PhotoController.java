package br.com.serratec.beestock.controller;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import br.com.serratec.beestock.exceptions.NotFindException;
import br.com.serratec.beestock.model.Photo;
import br.com.serratec.beestock.service.PhotoService;

@RestController
@RequestMapping("/fotos")
public class PhotoController {
    @Autowired
    private PhotoService photoService;

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @GetMapping("/{id}")
    public ResponseEntity<byte[]> findPhoto(@PathVariable Integer id) throws NotFindException {
        Photo photo = photoService.buscar(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("content-type", photo.getType());
        headers.add("content-length", String.valueOf(photo.getData().length));
        return new ResponseEntity<>(photo.getData(), headers, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @PostMapping()
    public ResponseEntity<?> postPhoto(@RequestPart Photo photo, @RequestParam MultipartFile file){
        Photo newPhoto;
        try {
            newPhoto = photoService.addPhoto(photo,file);
            HttpHeaders headers = new HttpHeaders();
            headers.add("content-type", newPhoto.getType());
            headers.add("content-length", String.valueOf(newPhoto.getData().length));
            return new ResponseEntity<>(newPhoto.getData(), headers, HttpStatus.CREATED);
        } catch (IOException | NotFindException e) {
            return ResponseEntity.unprocessableEntity().body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @PutMapping("/{id}")
    public ResponseEntity<?> attPhoto (@PathVariable Integer id, @RequestParam MultipartFile file){
        try {
            Photo newPhoto = photoService.attPhoto(id, file);
            HttpHeaders headers = new HttpHeaders();
            headers.add("content-type", newPhoto.getType());
            headers.add("content-length", String.valueOf(newPhoto.getData().length));
            return new ResponseEntity<>(newPhoto.getData(), headers, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.unprocessableEntity().body(e.getMessage());
        }
    }

}
