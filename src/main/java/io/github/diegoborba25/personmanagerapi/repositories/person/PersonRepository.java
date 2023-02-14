package io.github.diegoborba25.personmanagerapi.repositories.person;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.github.diegoborba25.personmanagerapi.model.person.Person;

/***
 * Repository interface for <code>Person</code>.
 * 
 * @author Diego Borba
 */
@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

}