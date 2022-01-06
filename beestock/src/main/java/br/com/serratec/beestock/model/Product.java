package br.com.serratec.beestock.model;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class Product {
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column (name = "id_product")
    private Integer id;

    @Column
    private String name;

    @NotBlank
    @Column
    private String codeSKU;

    @Column
    private String brand;

    @Positive
    @Column
    private Double cost;

    @Positive
    @Column
    private Double avgCost;

   @Enumerated(EnumType.STRING)
    private Availability availability;

    @Column
    private Double price;

    @Transient
    private Double markUp;

    @NotNull
    @PositiveOrZero
    private Integer currentQuantity;

    @Column
    private Integer minQuantity;

    @Column
    private Integer maxQuantity;

    @Column
    private String provider;

    @OneToOne(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private ProductPhoto productPhoto;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Shipping> shipping;

    @Enumerated(EnumType.ORDINAL)
    private ProductState state;

    @ManyToOne(cascade =CascadeType.ALL)
    @JoinColumn(name = "id_category")
    @JsonBackReference
    private Categories category;

    public Product() {
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

    public Availability getAvailability() {
        return availability;
    }

    public void setAvailability(Availability availability) {
        this.availability = availability;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public List<Shipping> getShipping() {
        return shipping;
    }

    public void setShipping(List<Shipping> shipping) {
        this.shipping = shipping;
    }

    public Double getMarkUp() {
        markUp = price - cost;
        return markUp;
    }

    public void setMarkUp(Double markUp) {
        this.markUp = markUp;
    }

    public Integer getCurrentQuantity() {
        return currentQuantity;
    }

    public void setCurrentQuantity(Integer currentQuantity) {
        this.currentQuantity = currentQuantity;
    }

    public Integer getMinQuantity() {
        return minQuantity;
    }

    public void setMinQuantity(Integer minQuantity) {
        this.minQuantity = minQuantity;
    }

    public Integer getMaxQuantity() {
        return maxQuantity;
    }

    public void setMaxQuantity(Integer maxQuantity) {
        this.maxQuantity = maxQuantity;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public ProductState getState() {
        return state;
    }

    public void setState(ProductState state) {
        this.state = state;
    }

    public Categories getCategory() {
        return category;
    }

    public void setCategory(Categories category) {
        this.category = category;
    }

    public ProductPhoto getProductPhoto() {
        return productPhoto;
    }

    public void setProductPhoto(ProductPhoto productPhoto) {
        this.productPhoto = productPhoto;
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
        Product other = (Product) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
}
