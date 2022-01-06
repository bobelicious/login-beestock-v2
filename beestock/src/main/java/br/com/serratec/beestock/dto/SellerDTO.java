package br.com.serratec.beestock.dto;

import br.com.serratec.beestock.model.UserModel;

public class SellerDTO {
    private Integer id;
    private String name;
    
    public SellerDTO(UserModel userModel) {
        this.id = userModel.getId();
        this.name = userModel.getName();
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
}
