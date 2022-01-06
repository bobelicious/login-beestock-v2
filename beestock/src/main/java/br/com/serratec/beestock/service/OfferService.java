package br.com.serratec.beestock.service;

import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Service;

import br.com.serratec.beestock.exceptions.NotFindException;
import br.com.serratec.beestock.model.Product;
import br.com.serratec.beestock.model.Offer;
import br.com.serratec.beestock.repository.ProductRepository;
import br.com.serratec.beestock.repository.OfferRepository;

@Service
public class OfferService {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    private OfferRepository saleRepository;

    public List<Offer> listAll(){
        return saleRepository.findAll();
    }

    public Optional<Offer> findById(Integer id) throws NotFindException{
        Optional<Offer> sale = saleRepository.findById(id);
        if(sale.isPresent()){
            return sale;
        }
        throw new NotFindException("Oferta não encontrada");
    }

    public Offer addSale(Offer sale){
        Optional<Product>product = productRepository.findById(sale.getProduct().getId());
        sale.setProduct(product.get());
        return saleRepository.save(sale);
    }

    public Offer attSale (Integer id, Offer sale) throws NotFindException{
        Optional <Offer> s = saleRepository.findById(id);
        if(s.isEmpty()){
            throw new NotFindException("Oferta não encontrada");
        }
        s.get().setDiscount((sale.getDiscount() == null) ? s.get().getDiscount() : sale.getDiscount());
        s.get().setShelfLife((sale.getShelfLife()==null) ? s.get().getShelfLife() : sale.getShelfLife());
        s.get().setMinQuantity((sale.getMinQuantity()==null)? s.get().getMinQuantity() : sale.getMinQuantity());
        s.get().setQuantityInOffer((sale.getQuantityInOffer()==null) ? s.get().getQuantityInOffer(): sale.getQuantityInOffer());
        s.get().setId(id);

        return saleRepository.save(s.get());
    }

    public void deleteById(Integer id) throws NotFindException{
        Optional<Offer> sale = saleRepository.findById(id);
        if(sale.isEmpty()){
            throw new NotFindException("Oferta não encontrada");
        }
        saleRepository.deleteById(id);
    }
}
