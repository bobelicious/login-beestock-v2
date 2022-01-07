package br.com.serratec.beestock.dto;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.serratec.beestock.model.Product;
import br.com.serratec.beestock.model.Availability;
import br.com.serratec.beestock.model.Offer;
import br.com.serratec.beestock.model.Shipping;

public class ProductDTO {
    private Integer id;
    private String codeSKU;
    private LocalDate date;
    private Availability availability;
    private String name;
    private String category;
    private String brand;
    private Double cost;
    private Double avgCost;
    private Integer currentQuantity;
    private Double price;
    private Offer sale;
    private Double markUp;
    private List<Shipping> shipping;
    private String photo;

    public ProductDTO(Product product) {
        this.id = product.getId();
        this.availability = product.getAvailability();
        this.name = product.getName();
        this.codeSKU = product.getCodeSKU();
        this.brand = product.getBrand();
        this.cost = product.getCost();
        this.avgCost = product.getAvgCost();
        this.currentQuantity = product.getCurrentQuantity();
        this.category = product.getCategory().getName();
        this.price = product.getPrice();
        this.shipping = product.getShipping();
        this.markUp = product.getMarkUp();
        this.photo = uriGenerator(product);
    }

    public String uriGenerator (Product product){
        Integer id = product.getProductPhoto().getId();
        String response;
        if(id==null){
            response="";
            return response;
        }
        URI uri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/produtos/{id}/foto")
        .buildAndExpand(id).toUri();
        response=uri.toString();
        return response;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCodeSKU() {
        return codeSKU;
    }

    public void setCodeSKU(String codeSKU) {
        this.codeSKU = codeSKU;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public Double getAvgCost() {
        return avgCost;
    }

    public void setAvgCost(Double avgCost) {
        this.avgCost = avgCost;
    }

    public Integer getCurrentQuantity() {
        return currentQuantity;
    }

    public void setCurrentQuantity(Integer currentQuantity) {
        this.currentQuantity = currentQuantity;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Offer getSale() {
        return sale;
    }

    public void setSale(Offer sale) {
        this.sale = sale;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public List<Shipping> getShipping() {
        return shipping;
    }

    public void setShipping(List<Shipping> shipping) {
        this.shipping = shipping;
    }

    public Double getMarkUp() {
        return markUp;
    }

    public void setMarkUp(Double markUp) {
        this.markUp = markUp;
    }

    public Availability getAvailability() {
        return availability;
    }

    public void setAvailability(Availability availability) {
        this.availability = availability;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
