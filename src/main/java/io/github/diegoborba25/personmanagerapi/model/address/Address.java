package io.github.diegoborba25.personmanagerapi.model.address;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/***
 * Class that implements the Address structure.
 * 
 * @author Diego Borba
 */
@Getter
@Setter
@NoArgsConstructor
@Entity(name = "address")
public class Address implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "streetAddress")
	private String streetAddress;

	@Column(name = "cep")
	private String cep;

	@Column(name = "number")
	private Integer number;

	@Column(name = "city")
	private String city;
}