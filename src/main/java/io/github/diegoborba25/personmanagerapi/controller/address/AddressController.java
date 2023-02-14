package io.github.diegoborba25.personmanagerapi.controller.address;

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
import io.github.diegoborba25.personmanagerapi.model.address.Address;
import io.github.diegoborba25.personmanagerapi.service.address.AddressServiceImpl;

/**
 * SpringBoot RestController that creates all service end-points related to
 * addresses.
 * 
 * @author Diego Borba
 */
@RestController
@RequestMapping("/person-manager-api/addresses")
public class AddressController extends AbstractController{

	@Autowired
	private AddressServiceImpl addressService;

	/**
	 * Method that creates a address.
	 * 
	 * @author Diego Borba
	 * @param address
	 * @return ResponseEntity with a <code>Address</code> object and the HTTP status
	 */
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<Address> create(@RequestBody JSONObject address) {
		try {
			if (addressService.isJSONValid(address)) {
				Address addressCreated = addressService.create(address);
				
				var uri = getUri(addressCreated.getStreetAddress());
				
				addressService.add(addressCreated);
				return ResponseEntity.created(uri).body(null);
			} else {
				return ResponseEntity.badRequest().body(null);
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
		}
	}

	/**
	 * Method that list all addresses.
	 * 
	 * @author Diego Borba
	 * @return ResponseEntity with a <code>List<Address></code> object and the HTTP
	 */
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Address>> find() {
		if (addressService.find().isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(addressService.find());
	}

	
	/**
	 * Method that find one especific address by Id.
	 * 
	 * @author Diego Borba
	 * @return ResponseEntity with a <code>Address</code> object and the HTTP status
	 */
	@GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Address> find(@PathVariable("id") long id) {
		if (Objects.isNull(id)) {
			return ResponseEntity.notFound().build();
		}
		
		Optional<Address> addressOptional = addressService.findById(id);
		if (addressOptional.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(addressOptional.get());
	}

	/**
	 * Method that updates a address.
	 * 
	 * @author Diego Borba
	 * @param address
	 * @return ResponseEntity with a <code>Address</code> object and the HTTP status
	 */
	@PutMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Address> update(@PathVariable("id") long id, @RequestBody JSONObject address) {
		try {
			if (addressService.isJSONValid(address)) {
				Optional<Address> addressToUpdate = addressService.findById(id);
				if (addressToUpdate.isEmpty()) {
					return ResponseEntity.notFound().build();
				} else {
					Address addressUpdated = addressService.update(addressToUpdate.get(), address);
					return ResponseEntity.ok(addressUpdated);
				}
			} else {
				return ResponseEntity.badRequest().body(null);
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
		}
	}

	/**
	 * Method that deletes all existing Addresses.
	 * 
	 * @author Diego Borba
	 * @return Returns an empty body with the HTTP status
	 */
	@DeleteMapping
	public ResponseEntity<Boolean> delete() {
		try {
			addressService.delete();
			return ResponseEntity.noContent().build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}
}