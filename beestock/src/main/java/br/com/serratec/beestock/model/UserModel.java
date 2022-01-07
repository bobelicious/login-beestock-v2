package br.com.serratec.beestock.model;

import java.util.*;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.*;

import br.com.serratec.beestock.dto.UserAddDTO;

@Entity
public class UserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_user")
    private Integer id;

    @Column
    private String name;

    @Column
    private String cpf;

    @Column
    private String rg;

    @Column
    private String email;

    @Column
    private String phone;

    @Column
    private String password;

    @OneToOne(mappedBy = "user", cascade=CascadeType.ALL)
    private Photo photo;

    @OneToMany(mappedBy = "id.user",fetch = FetchType.EAGER, cascade=CascadeType.REMOVE)
    private Set<UserProfile> usersProfiles = new HashSet<>();

    @ManyToOne(cascade=CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "id_address")
    @JsonBackReference
    private Address address;

    @OneToMany(mappedBy = "user")
    private List<OrderModel> orders;

    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;

    public UserModel() {
    }

    public UserModel(UserAddDTO usuarioDTO) {
        this.cpf = usuarioDTO.getCpf();
        this.rg = usuarioDTO.getRg();
        this.name = usuarioDTO.getName();
        this.email = usuarioDTO.getEmail();
        this.phone = usuarioDTO.getPhone();
        this.password = usuarioDTO.getPassword();
        this.usersProfiles = usuarioDTO.getUserProfiles();
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

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Photo getPhoto() {
        return photo;
    }

    public void setPhoto(Photo photo) {
        this.photo = photo;
    }

    public Set<UserProfile> getUsersProfiles() {
        return usersProfiles;
    }

    public void setUsersProfiles(Set<UserProfile> usersProfiles) {
        this.usersProfiles = usersProfiles;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
    
    public List<OrderModel> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderModel> orders) {
        this.orders = orders;
    }

    public UserStatus getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(UserStatus userStatus) {
        this.userStatus = userStatus;
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
        UserModel other = (UserModel) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
}
