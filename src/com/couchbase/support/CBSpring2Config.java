package com.couchbase.support;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.couchbase.config.AbstractCouchbaseConfiguration;
import org.springframework.data.couchbase.repository.config.EnableCouchbaseRepositories;

@Configuration
@EnableCouchbaseRepositories
public class CBSpring2Config extends AbstractCouchbaseConfiguration {

	@Override
	protected List<String> getBootstrapHosts() {
		return Arrays.asList("172.23.99.170", "172.23.99.171");
	}

	@Override
	protected String getBucketName() {
		return "BUCKETNAME";
	}

	@Override
	protected String getBucketPassword() {
		return "";
	}
}

// EOF