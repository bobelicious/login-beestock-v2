package br.com.serratec.beestock.service;

import java.time.LocalDate;
import java.util.*;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.serratec.beestock.model.Availability;
import br.com.serratec.beestock.model.Shipping;
import br.com.serratec.beestock.repository.ShippingRepository;

@Service
public class ShippingService {
    @Autowired
    private ShippingRepository shippingRepository;

    @Autowired
    private ProductService productService;
    @Transactional
    public List<Shipping> listAll (){
        return shippingRepository.findAll();
    }
    @Transactional
    public Optional<Shipping> findById(Integer id){
        Optional <Shipping> shipping = shippingRepository.findById(id);
        return shipping;
    }
    @Transactional
    public Shipping addShipping (Shipping shipping){
        shipping.setDeliveryDate(LocalDate.now());
        shipping.setBatchCode(batchGenerator());
        shipping.setAvgCost(shipping.getCost()/shipping.getQuantity());
        shipping.getProduct().setAvailability(Availability.DISPONIVEL);
        shippingRepository.save(shipping);
        productService.attQauntity(shipping.getProduct().getId(), shipping);
        return shipping;
    }

    private String batchGenerator(){
        String [] alphabet = {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"};
        String [] number = {"0","1","2","3","4","5","6","7","8","9"};
        Random r = new Random();
        String batchCode="";
        batchCode= number[r.nextInt(10)]+number[r.nextInt(10)]+" "+alphabet[r.nextInt(26)]+alphabet[r.nextInt(26)]+alphabet[r.nextInt(26)];
        return batchCode;
    }

}
