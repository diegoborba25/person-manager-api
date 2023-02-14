package io.github.diegoborba25.personmanagerapi.controller.person;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.github.diegoborba25.personmanagerapi.controller.AbstractController;
import io.github.diegoborba25.personmanagerapi.model.person.Person;
import io.github.diegoborba25.personmanagerapi.model.person.address.PersonAddress;
import io.github.diegoborba25.personmanagerapi.service.address.AddressServiceImpl;
import io.github.diegoborba25.personmanagerapi.service.person.PersonServiceImpl;
import io.github.diegoborba25.personmanagerapi.service.person.address.PersonAddressServiceImpl;

/**
 * SpringBoot RestController that creates all service end-points related to
 * persons.
 * 
 * @author Diego Borba
 */
@RestController
@RequestMapping("/person-manager-api/persons")
public class PersonController extends AbstractController {

	@Autowired
	private PersonServiceImpl personService;
	@Autowired
	private AddressServiceImpl addressService;
	@Autowired
	private PersonAddressServiceImpl personAddressService;

	/**
	 * Method that creates a person.
	 * 
	 * @author Diego Borba
	 * @param person
	 * @return ResponseEntity with a <code>Person</code> object and the HTTP status
	 */
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<String> create(@RequestBody JSONObject person) {
		try {
			if (personService.isJSONValid(person)) {
				Person personCreated = personService.create(person);

				var uri = getUri(personCreated.getName());

				personService.add(personCreated);
				return ResponseEntity.created(uri).body(null);
			} else {
				return ResponseEntity.badRequest().body(null);
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(e.toString());
		}
	}

	/**
	 * Method that list all persons.
	 * 
	 * @author Diego Borba
	 * @return ResponseEntity with a <code>List<Person></code> object and the HTTP
	 *         status
	 */
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Person>> find() {
		if (personService.find().isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(personService.find());
	}

	/**
	 * Method that find one especific person by Id.
	 * 
	 * @author Diego Borba
	 * @return ResponseEntity with a <code>Person</code> object and the HTTP status
	 */
	@GetMapping(path = "/{personId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Person> find(@PathVariable("personId") Long personId) {
		if (Objects.isNull(personId)) {
			return ResponseEntity.notFound().build();
		}

		Optional<Person> personOptional = personService.findById(personId);
		if (personOptional.isEmpty()) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(personOptional.get());
	}

	/**
	 * Method that updates a person.
	 * 
	 * @author Diego Borba
	 * @param jsonPerson
	 * @return ResponseEntity with a <code>Person</code> object and the HTTP status
	 */
	@PutMapping(path = "/{personId}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Person> update(@PathVariable("personId") long personId, @RequestBody JSONObject jsonPerson) {
		try {
			if (personService.isJSONValid(jsonPerson)) {
				Optional<Person> personToUpdate = personService.findById(personId);
				if (personToUpdate.isEmpty()) {
					return ResponseEntity.notFound().build();
				} else {
					Person personUpdated = personService.update(personToUpdate.get(), jsonPerson);
					return ResponseEntity.ok(personUpdated);
				}
			} else {
				return ResponseEntity.badRequest().body(null);
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
		}
	}

	/**
	 * Method that deletes all existing persons.
	 * 
	 * @author Diego Borba
	 * @return Returns an empty body with the HTTP status
	 */
	@DeleteMapping
	public ResponseEntity<Boolean> delete() {
		try {
			personService.delete();
			return ResponseEntity.noContent().build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	/**
	 * Method that creates many to many relationship between <code>Person</code> and
	 * <code>Address</code>.
	 * 
	 * @author Diego Borba
	 * @return ResponseEntity with a <code>PersonAddress</code> object and the HTTP
	 *         status
	 */
	@PostMapping(path = "/{personId}/addresses/{addressId}", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<PersonAddress> createPersonAddress(@PathVariable("personId") Long personId,
			@PathVariable("addressId") Long addressId, @RequestBody JSONObject jsonPersonAddress) {
		try {
			PersonAddress personAddressCreated = personAddressService.create(personId, addressId, jsonPersonAddress);

			if (personService.findById(personId).isEmpty() || addressService.findById(addressId).isEmpty()) {
				return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
			}

			if (personAddressService.exist(personAddressCreated)) {
				return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
			} else {
				personAddressService.add(personAddressCreated);
			}

			var uri = getUri(
					personAddressCreated.getPersonId().toString() + personAddressCreated.getAddressId().toString());
			return ResponseEntity.created(uri).body(null);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
		}
	}

	/**
	 * Method that list all person addresses.
	 * 
	 * @author Diego Borba
	 * @return ResponseEntity with a <code>List<PersonAddress></code> object and the
	 *         HTTP status
	 */
	@GetMapping(path = "/{personId}/addresses", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<PersonAddress>> findAddresses(@PathVariable("personId") Long personId) {
		List<PersonAddress> persons = personAddressService.find(personId);
		if (persons.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(persons);
	}

	/**
	 * Method that updates a person address.
	 * 
	 * @author Diego Borba
	 * @param jsonPersonAddress
	 * @return ResponseEntity with a <code>PersonAddress</code> object and the HTTP
	 *         status
	 */
	@PutMapping(path = "/{personId}/addresses/{addressId}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PersonAddress> updateAddress(@PathVariable("personId") Long personId,
			@PathVariable("addressId") Long addressId, @RequestBody JSONObject jsonPersonAddress) {
		try {
			if (personAddressService.isJSONValid(jsonPersonAddress)) {
				PersonAddress personToUpdate = personAddressService.find(personId, addressId);
				if (personToUpdate == null) {
					return ResponseEntity.notFound().build();
				} else {
					PersonAddress personUpdated = personAddressService.update(personToUpdate, jsonPersonAddress);
					return ResponseEntity.ok(personUpdated);
				}
			} else {
				return ResponseEntity.badRequest().body(null);
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
		}
	}

	/**
	 * Method that deletes all existing person addresses.
	 * 
	 * @author Diego Borba
	 * @return Returns an empty body with the HTTP status
	 */
	@DeleteMapping(path = "/addresses")
	public ResponseEntity<Boolean> deleteAddresses() {
		try {
			personAddressService.delete();
			return ResponseEntity.noContent().build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}
}