package io.github.diegoborba25.personmanagerapi.model.person.address;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/***
 * Class that implements the structure of relationship between
 * <code>Person</code> and <code>Address</code>.
 * 
 * @author Diego Borba
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "person_address")
public class PersonAddress implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "personId")
	private Long personId;

	@Column(name = "addressId")
	private Long addressId;

	@Column(name = "main")
	private boolean main;

	public PersonAddress(Long personId, Long addressId, boolean main) {
		this.setPersonId(personId);
		this.setAddressId(addressId);
		this.setMain(main);
	}

	public PersonAddress(Long personId) {
		this.setPersonId(personId);
	}
}