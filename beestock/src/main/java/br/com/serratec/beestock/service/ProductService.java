package br.com.serratec.beestock.service;

import java.io.IOException;
import java.util.*;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.com.serratec.beestock.dto.ProductDTO;
import br.com.serratec.beestock.dto.ProductPostDTO;
import br.com.serratec.beestock.exceptions.NotFindException;
import br.com.serratec.beestock.model.Availability;
import br.com.serratec.beestock.model.Categories;
import br.com.serratec.beestock.model.Product;
import br.com.serratec.beestock.model.Shipping;
import br.com.serratec.beestock.repository.CategoriesRepository;
import br.com.serratec.beestock.repository.ProductRepository;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductPhotoService productPhotoService;
    @Autowired
    private CategoriesRepository categoriesRepository;

    @Transactional
    public List<ProductDTO> findAll() {
        List<Product> products = productRepository.findAll();
        List<ProductDTO> productDTOs = new ArrayList<>();
        products.forEach((p) -> {
            productDTOs.add(new ProductDTO(p));
        });
        return productDTOs;
    }

    public ProductDTO findByCodeSKU (String codeSKU) throws NotFindException{
        Optional<Product> product = productRepository.findByCodeSKU(codeSKU);
        if(product.isPresent()){
            return new ProductDTO(product.get());
        }
        throw new NotFindException("Produto não encontrado");
    }

    @Transactional
    public ProductDTO findByID(Integer id) throws NotFindException {
        Optional<Product> product = productRepository.findById(id);
        if(product.isPresent()){
            return new ProductDTO(product.get());
        }
        throw new NotFindException("Produto não encontrado");
    }

    @Transactional
    public ProductPostDTO addProduct(Product product, MultipartFile file) throws IOException {
        product.setCategory(verify(product.getCategory()));
        product.setCodeSKU(skuGenerator());
        productPhotoService.insert(productRepository.save(product), file);
        return new ProductPostDTO(product);
    }

    private String skuGenerator(){
        String [] alphabet = {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"};
        String [] number = {"0","1","2","3","4","5","6","7","8","9"};
        Random r = new Random();
        String skuCode="";
        skuCode= alphabet[r.nextInt(26)]+alphabet[r.nextInt(26)]+alphabet[r.nextInt(26)]+number[r.nextInt(10)]+number[r.nextInt(10)]+number[r.nextInt(10)];
        return skuCode;
    }

    @Transactional
    private Categories verify(Categories category) {
        Optional<Categories> c = categoriesRepository.findByName(category.getName());
        Categories response = (c.isPresent()) ? c.get() : categoriesRepository.save(category);
        return response;
    }

    @Transactional
    public ProductDTO attProduct (Product product, MultipartFile file, Integer id) throws NotFindException, IOException{
        String name = product.getName();
        String codeSKU = product.getCodeSKU();
        String brand = product.getBrand();
        Double cost = product.getCost();
        Double avgCost = product.getAvgCost();
        Availability availability = product.getAvailability();
        Double markUp = product.getMarkUp();
        Integer minQuantity = product.getMinQuantity();
        Integer maxQuantity = product.getMaxQuantity();
        String provider = product.getProvider();
        Optional<Product> p = productRepository.findById(id);
        if(p.isEmpty()){
            throw new NotFindException("Produto não encontrado");
        }
        Product newProduct = p.get();
        name = (name == null) ? newProduct.getName() : name;
        codeSKU = (codeSKU == null) ? newProduct.getCodeSKU() : codeSKU;
        brand=(brand == null) ? newProduct.getBrand() : brand;
        cost = (cost==null) ? newProduct.getCost() : cost;
        avgCost = (avgCost==null) ?  newProduct.getAvgCost() : avgCost;
        availability=(availability==null) ? newProduct.getAvailability() : availability;
        markUp = (markUp==null) ? newProduct.getMarkUp() : markUp;
        minQuantity= (minQuantity==null) ? newProduct.getMinQuantity(): minQuantity;
        maxQuantity= (maxQuantity==null) ? newProduct.getMaxQuantity():maxQuantity;
        provider= (provider==null) ?  newProduct.getProvider(): provider;

        productPhotoService.insert(productRepository.save(newProduct), file);

        return new ProductDTO(newProduct);
    }

    @Transactional
    public void deleteProduct (Integer id) throws NotFindException{
       Optional<Product> product= productRepository.findById(id);
       if(product.isEmpty()){
           throw new NotFindException("Produto não encontrado");
       }
       productRepository.deleteById(id);
    }

    @Transactional
    public void attQauntity (Integer id, Shipping shipping){
        Optional< Product> product = productRepository.findById(id);
        Integer quantity = product.get().getCurrentQuantity();
        quantity = shipping.getQuantity() + quantity;
        product.get().setCurrentQuantity(quantity);
        product.get().setId(product.get().getId());
        product.get().setAvailability(Availability.DISPONIVEL);
        productRepository.save(product.get());
    }
}
