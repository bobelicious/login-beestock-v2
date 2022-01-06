package br.com.serratec.beestock.dto;

import java.time.LocalDate;
import java.util.List;

import br.com.serratec.beestock.model.Product;
import br.com.serratec.beestock.model.Availability;
import br.com.serratec.beestock.model.Offer;
import br.com.serratec.beestock.model.Shipping;

public class ProductDTO {
    private String codeSKU;
    private String name;
    private String category;
    private String brand;
    private Double cost;
    private Double avgCost;
    private Integer currentQuantity;
    private Double price;
    private Offer sale;
    private LocalDate date;
    private List<Shipping> shipping;
    private Integer id;
    private Double markUp;
    private Availability availability;

    public ProductDTO(Product product) {
        this.id = product.getId();
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
        this.availability = product.getAvailability();
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
}
