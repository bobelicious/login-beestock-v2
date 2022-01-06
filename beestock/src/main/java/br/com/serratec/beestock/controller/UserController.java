package br.com.serratec.beestock.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.serratec.beestock.dto.UserAttDTO;
import br.com.serratec.beestock.dto.UserDTO;
import br.com.serratec.beestock.dto.UserAddDTO;
import br.com.serratec.beestock.exceptions.CpfException;
import br.com.serratec.beestock.exceptions.EmailException;
import br.com.serratec.beestock.exceptions.NotFindException;
import br.com.serratec.beestock.model.Photo;
import br.com.serratec.beestock.service.PhotoService;
import br.com.serratec.beestock.service.UserService;

@RestController
@RequestMapping("/usuarios")
public class UserController {
    @Autowired
    UserService usuarioService;
    @Autowired
    PhotoService fotoService;

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @GetMapping
    public List<UserDTO> findAll() {
        return usuarioService.findAll();
    }

    @PostMapping
    public ResponseEntity<?> saveUsuario(@Valid @RequestBody UserAddDTO usuarioInserirDTO){
        try {
            UserDTO usuarioDTO = usuarioService.saveEntity(usuarioInserirDTO);
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(usuarioDTO.getId())
                    .toUri();
            return ResponseEntity.created(uri).body(usuarioDTO);
        } catch (EmailException | CpfException e) {
            return ResponseEntity.unprocessableEntity().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> attUser(@PathVariable Integer id,
            @Valid @RequestBody UserAttDTO usuarioAttDTO) {
        try {
            UserDTO usuarioDTO = usuarioService.attUsuario(usuarioAttDTO, id);
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(usuarioDTO.getId())
                    .toUri();
            return ResponseEntity.created(uri).body(usuarioDTO);
        } catch (Exception e) {
            return ResponseEntity.unprocessableEntity().body(e.getMessage());
        }
    }

    @GetMapping("/{id}/foto")
    public ResponseEntity<byte[]> buscarPorFoto(@PathVariable Integer id) throws NotFindException {
        Photo foto = fotoService.buscar(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("content-type", foto.getType());
        headers.add("content-length", String.valueOf(foto.getData().length));
        return new ResponseEntity<>(foto.getData(), headers, HttpStatus.OK);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Integer id){
        try {
            usuarioService.deleteUser(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.unprocessableEntity().body(e.getMessage());
        }
    }
}

