package br.com.serratec.beestock.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import br.com.serratec.beestock.model.OrderModel;
import br.com.serratec.beestock.model.OrderProduct;
import br.com.serratec.beestock.model.OrderStatus;

public class OrderDTO {
    private Integer id;
    private OrderStatus status;
    private String costumer;
    private Double totalPurchase;
    private LocalDate date;
    private SellerDTO seller;
    private List<OrderProductDTO> orderProducts;

    

    public OrderDTO(OrderModel order)  {
        this.id = order.getId();
        this.status = order.getOrderStatus();
        this.costumer = order.getCostumer();
        this.totalPurchase = order.getTotalPurchase();
        this.date = order.getDate();
        this.seller = new SellerDTO(order.getUser());
        this.orderProducts = getItemOrder(order.getOrdersProducts());
    }

    private List<OrderProductDTO> getItemOrder(List<OrderProduct> orderProducts){
        List<OrderProductDTO> orderProductDTOs = new ArrayList<>();
        for (OrderProduct orderProduct : orderProducts) {
            orderProductDTOs.add(new OrderProductDTO(orderProduct));
        }
        return orderProductDTOs;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCostumer() {
        return costumer;
    }

    public void setCostumer(String costumer) {
        this.costumer = costumer;
    }

    public Double getTotalPurchase() {
        return totalPurchase;
    }

    public void setTotalPurchase(Double totalPurchase) {
        this.totalPurchase = totalPurchase;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public SellerDTO getSeller() {
        return seller;
    }

    public void setSeller(SellerDTO seller) {
        this.seller = seller;
    }

    public List<OrderProductDTO> getOrderProducts() {
        return orderProducts;
    }

    public void setOrderProducts(List<OrderProductDTO> orderProducts) {
        this.orderProducts = orderProducts;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}
