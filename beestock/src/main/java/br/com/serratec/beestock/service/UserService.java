package br.com.serratec.beestock.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.serratec.beestock.dto.UserAttDTO;
import br.com.serratec.beestock.dto.UserDTO;
import br.com.serratec.beestock.dto.UserAddDTO;
import br.com.serratec.beestock.exceptions.CpfException;
import br.com.serratec.beestock.exceptions.EmailException;
import br.com.serratec.beestock.exceptions.NotFindException;
import br.com.serratec.beestock.model.Address;
import br.com.serratec.beestock.model.UserModel;
import br.com.serratec.beestock.model.UserProfile;
import br.com.serratec.beestock.repository.UserProfileRepository;
import br.com.serratec.beestock.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserProfileRepository userProfileRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProfileService profileService;
    @Autowired
    private AddressService addressService;

    /**
     * metodo que retorna a lista de todos os Usuarios
     * @return
     */
    public List<UserDTO> findAll(){
        List<UserModel> users = userRepository.findAll();
        List<UserDTO> usersDTOs = new  ArrayList<>();
        for (UserModel user : users) {
            usersDTOs.add(new UserDTO(user));
        }
        userLogged();
        return usersDTOs;
    }

    /**
     * metodo que recebe como parametro um id e retorna um usuario com base no id informado
     * @param id
     * @return
     * @throws NotFindException
     */
    public UserDTO findById(Integer id) throws NotFindException{
        Optional<UserModel> user = userRepository.findById(id);
        if(user.isPresent()){
            return new UserDTO(user.get());
        }
        throw new NotFindException("Usuario não encontrado");
    }
    /**
     * metodo que recebe um email como parametro e retorna um usuario com base no email informado
     * @param email
     * @return
     * @throws NotFindException
     */
    public UserDTO findByEmail (String email) throws NotFindException{
        Optional<UserModel>  user  = userRepository.findByEmail(email);
        if(user.isPresent()){
            return new UserDTO(user.get());
        }
        throw new NotFindException("Usuario não encontrado");
    }

    /**
     * recebe um json de usuario e persiste  ele no DB e retorna um usuarioDTO contendo apenas as informacoes necessarias.
     * @param userAttDTO
     * @return
     * @throws EmailException
     * @throws CpfException
     * @throws IOException
     */
    public UserDTO saveEntity (UserAddDTO userAttDTO) throws EmailException, CpfException{
       if(userRepository.findByEmail(userAttDTO.getEmail()).isPresent()){
           throw new EmailException("Email ja cadastrado");
       }else if(userRepository.findByCpf(userAttDTO.getCpf()).isPresent()){
           throw new CpfException("Cpf ja cadastrado");
       }
       userAttDTO.setPassword(bCryptPasswordEncoder.encode(userAttDTO.getPassword()));
        userAttDTO.setAddress(addressService.findAddress(userAttDTO.getAddress().getCep(), userAttDTO.getAddress().getNumber()));
       UserModel user = new UserModel(userAttDTO);
       user.setAddress(userAttDTO.getAddress());
       userRepository.save(user);

       for (UserProfile userProfile: userAttDTO.getUserProfiles()){
           userProfile.setUser(user);
           userProfile.setProfile(profileService.find(userProfile.getProfile().getId()));
       }
       userProfileRepository.saveAll(userAttDTO.getUserProfiles());
       return new UserDTO(user);
    }
    /**
     * recebe um DTO que permite a alteração de apenas algumas informações e verifica se um campo foi alterado ou mantido
     * @param userAttDTO
     * @param id
     * @return
     * @throws NotFindException
     */
    public UserDTO attUsuario (UserAttDTO userAttDTO, Integer id) throws NotFindException{  //*falta foto e cargo
        Optional<UserModel> u= userRepository.findById(id);
        Address address = userAttDTO.getAddress();
        String phone = userAttDTO.getPhone();
        String email = userAttDTO.getEmail();
        String password = userAttDTO.getPassword();
        if(u.isEmpty()){
            throw new NotFindException("Usuario não encontrado");
        }
        UserModel user = u.get();
        user.setId(id);
        user.setAddress(address = (address == null) ? user.getAddress() : addressService.findAddress(address.getCep(), address.getNumber()));
        user.setPhone(phone = (phone == null) ? user.getPhone() : phone);
        user.setEmail(email = (email == null) ? user.getEmail() : email);
        user.setPassword(password = (password == null) ? user.getPassword() :bCryptPasswordEncoder.encode(password));

        for (UserProfile userProfile: userAttDTO.getUserProfiles()){
            userProfile.setUser(user);
            userProfile.setProfile(profileService.find(userProfile.getProfile().getId()));
        }
        userProfileRepository.saveAll(userAttDTO.getUserProfiles());
       userRepository.save(user);
        return new UserDTO(user);
    }

    public void deleteUser(Integer id) throws NotFindException{
       Optional<UserModel> user = userRepository.findById(id);
       if(user.isEmpty()){
           throw new NotFindException("Usuario não encontrado");
        }
        userRepository.deleteById(id);
    }

    public UserModel userLogged(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String nome = ((UserDetails)principal).getUsername();
        Optional<UserModel> user = userRepository.findByCpf(nome);
        System.out.println(nome);
        return user.get();
    }
}
