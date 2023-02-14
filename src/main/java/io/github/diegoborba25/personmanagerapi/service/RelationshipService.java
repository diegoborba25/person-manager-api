package io.github.diegoborba25.personmanagerapi.service;

import java.util.List;

import org.json.simple.JSONObject;

import io.github.diegoborba25.personmanagerapi.model.person.address.PersonAddress;

/**
 * Interface with methods to a relationship type service implementation.
 * 
 * @author Diego Borba
 */
public interface RelationshipService<T> extends Service<T> {

	T create(Long idObject, Long idObject2, JSONObject jsonObject);

	List<T> find(Long idObject);
	
	PersonAddress find(Long idObject, Long idObject2);

	Boolean exist(PersonAddress personAddressCreated);
}