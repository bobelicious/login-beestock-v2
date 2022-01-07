package br.com.serratec.beestock.dto;

import br.com.serratec.beestock.model.Product;

public class ProductPostDTO {
    private String name;
    private String sku;

    public ProductPostDTO(Product product) {
        this.name = product.getName();
        this.sku = product.getCodeSKU();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }
}
