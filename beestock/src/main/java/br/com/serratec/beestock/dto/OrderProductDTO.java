package br.com.serratec.beestock.dto;


import br.com.serratec.beestock.model.OrderProduct;

public class OrderProductDTO {
    private Integer quantity;
    private Double unitPrice;
    private Double discount;
    private Double subtotal;
    private String product;
   
    public OrderProductDTO(OrderProduct orderProduct) {
        this.quantity=orderProduct.getQuantity();
        this.discount = orderProduct.getDiscount();
        this.unitPrice=orderProduct.getUnitPrice();
        this.subtotal=orderProduct.getSubtotal();
        this.product = orderProduct.getProduct().getName();
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }
}
