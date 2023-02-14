package io.github.diegoborba25.personmanagerapi.service;

import org.json.simple.JSONObject;

public abstract class AbstractObjectService<T> extends AbstractService<T> implements ObjectService<T> {

	protected abstract T createObject();

	/**
	 * Method to create a object.
	 * 
	 * @author Diego Borba
	 * @param jsonObject
	 * @return <code>T</code> object
	 */
	@Override
	public T create(JSONObject jsonObject) {
		T object = createObject();
		setValues(jsonObject, object);

		return object;
	}
}