package br.com.serratec.beestock.dto;

import org.springframework.beans.factory.annotation.*;

import br.com.serratec.beestock.model.Address;
import br.com.serratec.beestock.model.UserModel;
import br.com.serratec.beestock.model.UserProfile;
import br.com.serratec.beestock.repository.UserProfileRepository;
import br.com.serratec.beestock.service.ProfileService;

public class UserDTO {
    private Integer id;
    private String name;
    private String email;
    private String cpf;
    private String rg;
    private Address address;
    private String phone;
    private String cargo;

    @Autowired
    ProfileService profileService;
    @Autowired
    UserProfileRepository userProfileRepository;

    public UserDTO() {
    }

    public UserDTO(UserModel user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email =  user.getEmail();
        this.cpf =  user.getCpf();
        this.rg =  user.getRg();
        this.address =  user.getAddress();
        this.phone =  user.getPhone();
        this.cargo = getCargo(user);
    }

    private String getCargo(UserModel user){
        String cargo = "";
        for (UserProfile userProfile : user.getUsersProfiles()) {
		     cargo =	userProfile.getProfile().getCargo();
		}
        return cargo;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public ProfileService getProfileService() {
        return profileService;
    }

    public void setProfileService(ProfileService profileService) {
        this.profileService = profileService;
    }

    public UserProfileRepository getUserProfileRepository() {
        return userProfileRepository;
    }

    public void setUserProfileRepository(UserProfileRepository userProfileRepository) {
        this.userProfileRepository = userProfileRepository;
    }
}
