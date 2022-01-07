package br.com.serratec.beestock.service;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;
import org.springframework.web.multipart.MultipartFile;

import br.com.serratec.beestock.exceptions.NotFindException;
import br.com.serratec.beestock.model.Photo;
import br.com.serratec.beestock.repository.PhotoRepository;

@Service
public class PhotoService {
    @Autowired
    private PhotoRepository photoRepository;

    public Photo addPhoto(Photo photo,MultipartFile file) throws IOException, NotFindException{
        Photo newPhoto = new Photo();
        newPhoto.setName(file.getName());
        newPhoto.setData(file.getBytes());
        newPhoto.setType(file.getContentType());
        photoRepository.save(newPhoto);
        return newPhoto;
    }

    public Photo buscar(Integer id) throws NotFindException{
        Optional<Photo> photo = photoRepository.findById(id);
        if (photo.isPresent()){
            return photo.get();
        }
        throw new NotFindException("foto não encontrada");
    }

    public Photo attPhoto (Integer id,MultipartFile file) throws NotFindException, IOException{
        Optional<Photo> photo = photoRepository.findById(id);
        if(photo.isEmpty()){
            throw new NotFindException("foto do usuario não encontrada");
        }
        photo.get().setData(file.getBytes());
        photo.get().setName(file.getName());
        photo.get().setType(file.getContentType());
        photoRepository.save(photo.get());
        return photo.get();
    }
}