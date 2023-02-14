package io.github.diegoborba25.personmanagerapi.repositories.address;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.github.diegoborba25.personmanagerapi.model.address.Address;

/***
 * Repository interface for <code>Address</code>.
 * 
 * @author Diego Borba
 */
@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

}