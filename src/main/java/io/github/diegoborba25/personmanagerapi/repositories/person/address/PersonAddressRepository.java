package io.github.diegoborba25.personmanagerapi.repositories.person.address;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import io.github.diegoborba25.personmanagerapi.model.person.address.PersonAddress;

/***
 * Repository interface for <code>PersonAddress</code>.
 * 
 * @author Diego Borba
 */
@Repository
public interface PersonAddressRepository extends JpaRepository<PersonAddress, Long> {

	/**
	 * Manual query to get person addresses by id.
	 * 
	 * @author Diego Borba
	 * @param id
	 * @return <code>List<Person></code>
	 */
	@Query("SELECT p FROM person_address p WHERE p.personId=?1")
	List<PersonAddress> findByPersonId(Long personId);
	
	/**
	 * Manual query to get person addresses by id.
	 * 
	 * @author Diego Borba
	 * @param id
	 * @return <code>List<Person></code>
	 */
	@Query("SELECT p FROM person_address p WHERE p.personId=?1 and p.addressId=?2")
	PersonAddress findByIds(Long personId, Long addressId);
}