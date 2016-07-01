package com.couchbase.support;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.couchbase.support.entities.User;

// Brian Williams
// Started June 30, 2016

// Couchbase Spring Connector example

// References
// http://projects.spring.io/spring-data-couchbase/

// "Spring Data is an umbrella project 
// consisting of independent projects with, 
// in principle, different release cadences."

// The current versions as of today are:
// 
// 2.1.2
//    http://docs.spring.io/spring-data/couchbase/docs/2.1.2.RELEASE/reference/html
//    http://docs.spring.io/spring-data/couchbase/docs/2.1.2.RELEASE/api/
// 
//    Version 2.1.2 uses Couchbase Java SDK 2.2.8 under the covers
// 
// 1.4.4
//     http://docs.spring.io/spring-data/couchbase/docs/1.4.4.RELEASE/reference/html
//     http://docs.spring.io/spring-data/couchbase/docs/1.4.4.RELEASE/api/

// Further References:

// JPA == the Java Persistence API
// http://stackoverflow.com/questions/16148188/spring-data-jpa-versus-jpa-whats-the-difference

// Autowire , Autowiring
// http://stackoverflow.com/questions/3153546/how-does-autowiring-work-in-spring



public class CBSpring2 {

	// Please refer to the CBSpring2Config class to see where the Couchbase cluster
	// hosts and bucket name are specified

	public static void main(String[] args) {

		SupportUtils.printCenteredBanner("Welcome.  I am running on host " + SupportUtils.getHostname() + " and current time is " 
				+ SupportUtils.getCurrentTimeStamp());
		
		String origin = SupportUtils.getHostname() + " " + SupportUtils.getCurrentTimeStamp();
		
		SupportUtils.printCenteredBanner("I will initialize the application context from the classpath.");
		SupportUtils.printCenteredBanner("These folders are in the classpath at this time:");

		SupportUtils.showClasspath();

		System.out.println("The current Working Directory is " + System.getProperty("user.dir"));

		// This looks in the classpath
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("./applicationContext.xml");

		User user1 = new User("user1", "John", "Smith", origin);

		UserRepository  repo = ctx.getBean(UserRepository.class);

		repo.save(user1);

		ctx.close();

		// The document that is created in Couchbase looks like this, when you lookup user1 in the UI

		// {
		//	  "firstname": "John",
		//	  "_class": "com.couchbase.support.entities.User",
		//	  "lastname": "Smith"
		// }

		SupportUtils.printCenteredBanner("Goodbye");

	} // main()

} // class CBSpring2

// EOF