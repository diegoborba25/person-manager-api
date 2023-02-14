package io.github.diegoborba25.personmanagerapi.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.json.simple.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class AbstractService<T> implements Service<T> {

	/**
	 * Method that check if JSON is valid.
	 * 
	 * @author Diego Borba
	 * @param jsonInString
	 * @return <code>boolean</code>
	 */
	public boolean isJSONValid(JSONObject jsonObject) {
		try {
			return new ObjectMapper().readTree(jsonObject.toString()) != null;
		} catch (IOException e) {
			return false;
		}
	}

	abstract protected void setValues(JSONObject jsonObject, T object);

	abstract public void add(T object);
	
	/**
	 * Method to update a object.
	 * 
	 * @author Diego Borba
	 * @param object
	 * @param jsonObject
	 * @return <code>T</code> object
	 */
	@Override
	public T update(T object, JSONObject jsonObject) {
		setValues(jsonObject, object);
		add(object);
		return object;
	}

	/**
	 * Method to parse a String field.
	 * 
	 * @author Diego Borba
	 * @param object
	 * @return <code>String</code> object
	 */
	protected String parseString(Object object) {
		return (String) object;
	}

	/**
	 * Method to parse an LocalDateTime field.
	 * 
	 * @author Diego Borba
	 * @param object
	 * @return <code>LocalDateTime</code> object
	 */
	protected LocalDateTime parseLocalDateTime(Object object) {
		DateTimeFormatter formatter = DateTimeFormatter.ISO_INSTANT;
		return ZonedDateTime.parse((String) object, formatter.withZone(ZoneId.of("UTC"))).toLocalDateTime();
	}

	/**
	 * Method to parse a Boolean field.
	 * 
	 * @author Diego Borba
	 * @param object
	 * @return <code>Boolean</code> object
	 */
	protected Boolean parseBoolean(Object object) {
		return Boolean.getBoolean((String) object);
	}

	/**
	 * Method to parse a Integer field.
	 * 
	 * @author Diego Borba
	 * @param object
	 * @return <code>Integer</code> object
	 */
	protected Integer parseInteger(Object object) {
		return (Integer) object;
	}
}