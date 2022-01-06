package br.com.serratec.beestock.dto;

import java.util.*;

import br.com.serratec.beestock.model.Address;
import br.com.serratec.beestock.model.UserProfile;

public class UserAttDTO {
    private String name;
    private String email;
    private String phone;
    private String password;
    private Address address;

    private Set<UserProfile> userProfiles = new HashSet<>();

    public UserAttDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Set<UserProfile> getUserProfiles() {
        return userProfiles;
    }

    public void setUserProfiles(Set<UserProfile> userProfiles) {
        this.userProfiles = userProfiles;
    }

    
}
