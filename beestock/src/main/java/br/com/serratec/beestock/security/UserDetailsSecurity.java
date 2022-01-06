package br.com.serratec.beestock.security;

import java.util.*;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.serratec.beestock.model.UserModel;
import br.com.serratec.beestock.model.UserProfile;

public class UserDetailsSecurity implements UserDetails {
    private static final long serialVersionUID =1L;
    private Optional<UserModel> user;

    public UserDetailsSecurity(Optional<UserModel> user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
		for (UserProfile userProfile : user.get().getUsersProfiles()) {
			grantedAuthorities.add(new SimpleGrantedAuthority(userProfile.getProfile().getCargo()));
		}

		return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return user.get().getPassword();
    }

    @Override
    public String getUsername() {
        return user.get().getCpf();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
