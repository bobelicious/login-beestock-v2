package br.com.serratec.beestock.service;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;
import org.springframework.web.multipart.MultipartFile;

import br.com.serratec.beestock.exceptions.EmailException;
import br.com.serratec.beestock.exceptions.NotFindException;
import br.com.serratec.beestock.model.Photo;
import br.com.serratec.beestock.repository.PhotoRepository;

@Service
public class PhotoService {
    @Autowired
    private PhotoRepository fotoRepository;

    public Photo inserir(MultipartFile file) throws IOException, EmailException {
        Photo photo = new Photo();
        photo.setName(file.getName());
        photo.setData(file.getBytes());
        photo.setType(file.getContentType());
        fotoRepository.save(photo);
        return photo;
    }

    public Photo buscar(Integer id) throws NotFindException{
        Optional<Photo> photo = fotoRepository.findById(id);
        if (photo.isPresent()){
            return photo.get();
        }
        throw new NotFindException("foto não encontrada");
    }
}