package com.couchbase.support;

import java.util.List;

import org.springframework.data.couchbase.repository.CouchbaseRepository;

import com.couchbase.support.entities.User;

public interface UserRepository extends CouchbaseRepository<User, Long> {

	User findByFirstname(String firstName);

	List<User> findByLastname(String lastName);
}
