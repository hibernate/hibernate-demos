# hibernate-ogm-spring-data-jpa

This is a simple example about using Hibernate OGM with Spring Data JPA, it saves an entity on Neo4j embedded
and execute a native query to get it back.

## Building the project

Execute the following command to build the project:

    mvn clean install

This will execute some simple JUnit tests which use JPA's RESOURCE_LOCAL mode to access the datastore.

Any feedback on this demo is highly welcome, just send a mail to the hibernate-dev mailing list.
