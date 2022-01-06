package br.com.serratec.beestock.service;

import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.web.client.*;
import br.com.serratec.beestock.model.Address;
import br.com.serratec.beestock.repository.AddressRepository;

@Service
public class AddressService {

	@Autowired
	private AddressRepository addressRepository;

    /**
     * metodo que recebe como parametro um cep faz a consulta no banco de dados,
     * caso o cep se encontre no DB retorna o endereço, caso não, faz a busca no via cep
     * salva no DB e retorna o endereço
     * @param cep
     * @return
     * @throws HttpClientErrorException
     */
	public Address findAddress(String cep, Integer number) throws HttpClientErrorException{
			RestTemplate restTemplate = new RestTemplate();
			String uriViaCep = "https://viacep.com.br/ws/" + cep + "/json/";
			Optional<Address> addressViaCep = Optional.ofNullable(restTemplate.getForObject(uriViaCep, Address.class));
			if (addressViaCep.get().getCep() != null) {
				String cepWithoutLine = addressViaCep.get().getCep().replace("-", "");
				addressViaCep.get().setCep(cepWithoutLine);
				addressViaCep.get().setNumber(number);
				return insertAddress(addressViaCep.get());
			} else {
				return null;
			}
	}

	public Address insertAddress(Address address) {
		Address newAddress = new Address();
		newAddress.setBairro(address.getBairro());
		newAddress.setCep(address.getCep());
		newAddress.setLocalidade(address.getLocalidade());
		newAddress.setUf(address.getUf());
		newAddress.setLogradouro(address.getLogradouro());
		newAddress.setNumber(address.getNumber());
		
		address = addressRepository.save(address);
		return address;
	}

}
