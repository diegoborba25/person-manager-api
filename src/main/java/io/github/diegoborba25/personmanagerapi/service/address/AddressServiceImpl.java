package io.github.diegoborba25.personmanagerapi.service.address;

import java.util.List;
import java.util.Optional;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.diegoborba25.personmanagerapi.model.address.Address;
import io.github.diegoborba25.personmanagerapi.repositories.address.AddressRepository;
import io.github.diegoborba25.personmanagerapi.service.AbstractObjectService;

/**
 * Service that implements methods related to Address.
 * 
 * @author Diego Borba
 */
@Service
public class AddressServiceImpl extends AbstractObjectService<Address> {

	@Autowired
	AddressRepository addressRepository;

	/**
	 * Method to fullfil the <code>Address</code> object.
	 * 
	 * @author Diego Borba
	 * @param jsonAddress
	 * @param address
	 */
	protected void setValues(JSONObject jsonAddress, Address address) {

		String streetAddress = parseString(jsonAddress.get("streetAddress"));
		address.setStreetAddress(streetAddress != null ? streetAddress : address.getStreetAddress());

		String cep = parseString(jsonAddress.get("cep"));
		address.setCep(cep != null ? cep : address.getCep());

		Integer number = parseInteger(jsonAddress.get("number"));
		address.setNumber(number != null ? number : address.getNumber());

		String city = parseString(jsonAddress.get("city"));
		address.setCity(city != null ? city : address.getCity());
	}

	/**
	 * Method that return an instance of <code>Address</code>.
	 * 
	 * @author Diego Borba
	 * @return <code>Address</code>
	 */
	@Override
	protected Address createObject() {
		return new Address();
	}

	/**
	 * Method that add an <code>Address</code> object.
	 * 
	 * @author Diego Borba
	 * @param address
	 */
	@Override
	public void add(Address address) {
		addressRepository.save(address);
	}

	/**
	 * Method that get all addresses.
	 * 
	 * @author Diego Borba
	 * @param addresses
	 * @return <code>List<Address></code>
	 */
	@Override
	public List<Address> find() {
		return addressRepository.findAll();
	}

	/**
	 * Method that get a address by id.
	 * 
	 * @author Diego Borba
	 * @param id
	 * @return <code>Address</code>
	 */
	@Override
	public Optional<Address> findById(Long id) {
		return addressRepository.findById(id);
	}

	/**
	 * Method that deletes all addresses created.
	 * 
	 * @author Diego Borba
	 */
	@Override
	public void delete() {
		addressRepository.deleteAll();
	}
}