package io.github.diegoborba25.personmanagerapi.controller;

import java.net.URI;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 * Abstract class with base methods to any controller.
 * 
 * @author Diego Borba
 */
public abstract class AbstractController {
	
	protected URI getUri(String property) {
		return ServletUriComponentsBuilder.fromCurrentRequest().path("/" + property).build().toUri();
	}
}
