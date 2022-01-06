package br.com.serratec.beestock.service;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;
import org.springframework.web.multipart.MultipartFile;

import br.com.serratec.beestock.model.Product;
import br.com.serratec.beestock.model.ProductPhoto;
import br.com.serratec.beestock.repository.ProductPhotoRepository;

@Service
public class ProductPhotoService {
    @Autowired
    private ProductPhotoRepository productPhotoRepository;

    public ProductPhoto insert(Product product, MultipartFile file) throws IOException{
        ProductPhoto  photo = new ProductPhoto();
        photo.setData(file.getBytes());
        photo.setType(file.getContentType());
        photo.setProduct(product);
        productPhotoRepository.save(photo);
        return photo;
    }

    public ProductPhoto search(Integer id){
        Optional<ProductPhoto > photo = productPhotoRepository.findById(id);
        if (photo.isPresent()){
            return photo.get();
        }
        return null;
    }
}
