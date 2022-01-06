package br.com.serratec.beestock.model;

import java.io.Serializable;

import javax.persistence.*;

@Embeddable
public class UserProfilePK implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @ManyToOne
    @JoinColumn(name="id_profile")
    private Profile profile;

    @ManyToOne
    @JoinColumn(name="id_user")
    private UserModel user;

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }
}
