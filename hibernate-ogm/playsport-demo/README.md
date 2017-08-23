# Play Sport: Hibernate OGM (Infinispan Remote) Demo

Hibernate OGM can help write applications that interacts with a nosql database, avoiding writing a lot of "boilerplate code".
OGM is based on the JPA standard notation and it is integrated with JTA lifecycle.
 
This demo is designed for Wildfly 10 JEE 7 container, but adding some dependencies it can work well as a standalone java application.
The next version will be tested for Openshift Container Platform.

In this demo, some simplifications are made, such as exposing directly Hibernate entities through Rest services. 
For more realistic production cases, you can use the framework as mapstruct (see http://mapstruct.org/) to easy translate entities into dtos and vice versa.

Just launching the command: 
```
mvn clean install 
```
the entire test infrastructure will be created and managed by the Arquillian framework (see http://arquillian.org/).

Our nosql data store here is Infinispan Server (see http://infinispan.org/docs/stable/server_guide/server_guide.html).
Hibernate OGM allows to work with infinispan remote caches using the known JPA interface.
With the help of Hibernate Search we can easily add lucene indexes to the cache fields.

## Involved components

We need an Infinispan Remote Server and a Wildfly Application Server containing static modules of hibernate ogm and relative versions of hibernate orm, hibernate search and infinispan (hot rod) client.
Once the hibernate ogm version is choose, all other versions will be established:

| Hibernate OGM      | Hibernate ORM    | Hibernate Search | Infinispan Client/Server  |
| ------------------ |:----------------:|:----------------:|:-------------------------:|
| 5.2.0-SNAPSHOT*    | 5.1.9.Final      | 5.6.2.Final      | 8.2.6.Final               |
| 5.1.0.Final        | 5.1.4.Final      | 5.6.1.Final      | 8.2.5.Final               |

Wildfly __10.1.0.Final__ version is on the list of tested versions for Hibernate OGM 5.x versions.
__5.2.0-SNAPSHOT__ is a not final version, so the other component versions could change.
To always choose the right version of the components always refer to the official bom:

```xml
<dependency>
  <groupId>org.hibernate.ogm</groupId>
  <artifactId>hibernate-ogm-bom</artifactId>
  <version>${version.hibernate.ogm}</version>
  <type>pom</type>
  <scope>import</scope>
</dependency>
```




