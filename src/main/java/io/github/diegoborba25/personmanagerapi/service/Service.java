package io.github.diegoborba25.personmanagerapi.service;

import org.json.simple.JSONObject;

/**
 * Interface with base methods to any service.
 * 
 * @author Diego Borba
 */
public interface Service<T> {

	T update(T object, JSONObject jsonObject);

	void delete();
}