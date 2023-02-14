package io.github.diegoborba25.personmanagerapi.service;

import java.util.List;
import java.util.Optional;

import org.json.simple.JSONObject;

/**
 * Interface with methods to a Object(Entity) service implementation.
 * 
 * @author Diego Borba
 */
public interface ObjectService<T> extends Service<T> {

	T create(JSONObject jsonObject);

	List<T> find();

	Optional<T> findById(Long id);
}