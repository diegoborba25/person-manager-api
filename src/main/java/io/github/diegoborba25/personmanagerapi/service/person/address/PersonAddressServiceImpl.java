package io.github.diegoborba25.personmanagerapi.service.person.address;

import java.util.List;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import io.github.diegoborba25.personmanagerapi.model.person.address.PersonAddress;
import io.github.diegoborba25.personmanagerapi.repositories.person.address.PersonAddressRepository;
import io.github.diegoborba25.personmanagerapi.service.AbstractService;
import io.github.diegoborba25.personmanagerapi.service.RelationshipService;

/**
 * Service that implements methods related to PersonAddress.
 * 
 * @author Diego Borba
 */
@Service
public class PersonAddressServiceImpl extends AbstractService<PersonAddress>
		implements RelationshipService<PersonAddress> {

	@Autowired
	PersonAddressRepository personAddressRepository;

	/**
	 * Method to fullfil the <code>PersonAddress</code> object.
	 * 
	 * @author Diego Borba
	 * @param jsonPerson
	 * @param personAddress
	 */
	protected void setValues(JSONObject jsonPerson, PersonAddress personAddress) {
		Boolean main = (Boolean) jsonPerson.get("main");
		personAddress.setMain(main != null ? main : personAddress.isMain());
	}

	/**
	 * Method that return an instance of <code>PersonAddress</code>.
	 * 
	 * @author Diego Borba
	 * @param personId
	 * @param addressId
	 * @param jsonPersonAddress
	 * @return personAddress
	 */
	@Override
	public PersonAddress create(Long personId, Long addressId, JSONObject jsonPersonAddress) {
		PersonAddress personAddress = new PersonAddress(personId, addressId, true);
		setValues(jsonPersonAddress, personAddress);
		return personAddress;
	}

	/**
	 * Method that add a <code>PersonAddress</code> object.
	 * 
	 * @author Diego Borba
	 * @param personAddress
	 */
	@Override
	public void add(PersonAddress personAddress) {
		personAddressRepository.save(personAddress);
	}

	/**
	 * Method that get person addresses by personId.
	 * 
	 * @author Diego Borba
	 * @param personId
	 * @return <code>List<PersonAddress></code>
	 */
	@Override
	public List<PersonAddress> find(Long personId) {
		return personAddressRepository.findByPersonId(personId);
	}

	/**
	 * Method that get person address by personId and addressId.
	 * 
	 * @author Diego Borba
	 * @param personId
	 * @return <code>PersonAddress</code>
	 */
	@Override
	public PersonAddress find(Long personId, Long addressId) {
		return personAddressRepository.findByIds(personId, addressId);
	}

	/**
	 * Method that verify if the person address already exist.
	 * 
	 * @author Diego Borba
	 * @param personAddress
	 * @return <code>Boolean</code>
	 */
	@Override
	public Boolean exist(PersonAddress personAddress) {
		return !personAddressRepository.findAll(Example.of(personAddress)).isEmpty();
	}

	/**
	 * Method that deletes all persons addresses created.
	 * 
	 * @author Diego Borba
	 */
	@Override
	public void delete() {
		personAddressRepository.deleteAll();
	}
}