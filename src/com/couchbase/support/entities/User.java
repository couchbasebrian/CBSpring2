package com.couchbase.support.entities;

import org.springframework.data.couchbase.core.mapping.Document;

import com.couchbase.client.java.repository.annotation.Field;
import com.couchbase.client.java.repository.annotation.Id;

// http://stackoverflow.com/questions/9753918/entity-entity-cannot-be-resolved-to-a-type

// http://docs.spring.io/spring-data/couchbase/docs/current/reference/html/
// says
// "All entities should be annotated with the @Document annotation."

@Document
public class User {

	// "There is also a special @Id annotation which needs to be always in place." 
	// "Best practice is to also name the property id."

	@Id
	private String id;

	@Field
	private String firstname;

	@Field
	private String lastname;

	@Field
	private String origin;

	public User(String id, String firstname, String lastname, String origin) {
		this.id = id;
		this.firstname = firstname;
		this.lastname = lastname;
		this.origin = origin;
	}

	public String getId() {
		return id;
	}

	public String getFirstname() {
		return firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public String getOrigin() {
		return origin;
	}

}

// EOF