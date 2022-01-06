package br.com.serratec.beestock.model;

import java.time.LocalDate;

import javax.persistence.*;
import javax.validation.constraints.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class Offer {
    
    @Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name="Id_sale")
    private Integer id;

    @Positive(message = "Desconto não pode ser negativo")
    @Column
    private Double discount;

    @Column
    private LocalDate shelfLife;

    @PositiveOrZero(message="Quantidade em oferta não pode ser negativa ou zero")
    @Column
    private Integer minQuantity;

    @PositiveOrZero
    @Column
    private Integer quantityInOffer;

    @Column
    private String obs;

    @ManyToOne(cascade=CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name="id_product")
    @JsonBackReference
    private Product product;

    public Offer() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public LocalDate getShelfLife() {
        return shelfLife;
    }

    public void setShelfLife(LocalDate shelfLife) {
        this.shelfLife = shelfLife;
    }

    public Integer getMinQuantity() {
        return minQuantity;
    }

    public void setMinQuantity(Integer minQuantity) {
        this.minQuantity = minQuantity;
    }

    public Integer getQuantityInOffer() {
        return quantityInOffer;
    }

    public void setQuantityInOffer(Integer quantityInOffer) {
        this.quantityInOffer = quantityInOffer;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Offer other = (Offer) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
}
