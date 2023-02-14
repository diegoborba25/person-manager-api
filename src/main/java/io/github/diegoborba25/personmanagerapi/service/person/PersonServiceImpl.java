package io.github.diegoborba25.personmanagerapi.service.person;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.diegoborba25.personmanagerapi.model.person.Person;
import io.github.diegoborba25.personmanagerapi.repositories.person.PersonRepository;
import io.github.diegoborba25.personmanagerapi.service.AbstractObjectService;

/**
 * Service that implements methods related to Persons.
 * 
 * @author Diego Borba
 */
@Service
public class PersonServiceImpl extends AbstractObjectService<Person> {

	@Autowired
	PersonRepository personRepository;

	/**
	 * Method to fullfil the Person object.
	 * 
	 * @author Diego Borba
	 * @param jsonPerson
	 * @param person
	 */
	protected void setValues(JSONObject jsonPerson, Person person) {
		String name = parseString(jsonPerson.get("name"));
		person.setName(name != null ? name : person.getName());

		LocalDateTime birthday = parseLocalDateTime(jsonPerson.get("birthday"));
		person.setBirthday(birthday != null ? birthday : person.getBirthday());
	}

	/**
	 * Method that return an instance of <code>Person</code>.
	 * 
	 * @author Diego Borba
	 * @return <code>Person</code>
	 */
	@Override
	protected Person createObject() {
		return new Person();
	}

	/**
	 * Method that add a <code>Person</code> object.
	 * 
	 * @author Diego Borba
	 * @param person
	 */
	@Override
	public void add(Person person) {
		personRepository.save(person);
	}

	/**
	 * Method that get all persons.
	 * 
	 * @author Diego Borba
	 * @param persons
	 * @return <code>List<Person></code>
	 */
	@Override
	public List<Person> find() {
		return personRepository.findAll();
	}

	/**
	 * Method that get a person by id.
	 * 
	 * @author Diego Borba
	 * @param id
	 * @return <code>Person</code>
	 */
	@Override
	public Optional<Person> findById(Long id) {
		return personRepository.findById(id);
	}

	/**
	 * Method that deletes all persons created.
	 * 
	 * @author Diego Borba
	 */
	@Override
	public void delete() {
		personRepository.deleteAll();
	}
}