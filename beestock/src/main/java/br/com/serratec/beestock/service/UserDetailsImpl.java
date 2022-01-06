package br.com.serratec.beestock.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.serratec.beestock.model.UserModel;
import br.com.serratec.beestock.repository.UserRepository;
import br.com.serratec.beestock.security.UserDetailsSecurity;

@Service
public class UserDetailsImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    public UserDetailsImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String cpf) throws UsernameNotFoundException {
        Optional<UserModel> user = userRepository.findByCpf(cpf);

        if(!user.isPresent()){
            throw new RuntimeException();
        }
        return new UserDetailsSecurity(user);
    }
}