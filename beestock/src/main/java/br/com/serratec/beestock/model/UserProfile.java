package br.com.serratec.beestock.model;
import javax.persistence.*;

@Entity
@Table(name = "user_profile")
public class UserProfile {
    
    @EmbeddedId
    private UserProfilePK id = new UserProfilePK();

    public UserProfile(){

    }

    public UserProfile(UserModel user, Profile profile) {
        id.setUser(user);
        id.setProfile(profile);
    }

    public UserProfilePK getId() {
		return id;
	}

	public void setId(UserProfilePK id) {
		this.id = id;
	}

	public void setUser(UserModel user) {
		id.setUser(user);
	}

	public UserModel getUser() {
		return id.getUser();
	}

	public void setProfile(Profile profile) {
		id.setProfile(profile);
	}

	public Profile getProfile() {
		return id.getProfile();
	}
}
