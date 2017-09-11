# Play Sport: Hibernate OGM (Infinispan Remote) Demo

Hibernate OGM can help write applications that interacts with a nosql database, avoiding writing a lot of "boilerplate code".
OGM is based on the JPA standard and it is integrated with the JTA lifecycle.
 
This demo is designed for Wildfly 10 JEE 7 container, but adding some dependencies it can work well as a standalone java application.
The next version will be tested for Openshift Container Platform.

In this demo some simplifications are made, such as exposing directly Hibernate entities through REST services. 
For more realistic production cases, you can use the framework as mapstruct (see http://mapstruct.org/) to easy translate entities into dtos and vice versa.

Just launching the command: 
```
mvn clean install 
```
the entire test infrastructure will be created and managed by the Arquillian framework (see http://arquillian.org/).

Our NoSQL data store in this example is Infinispan Server (see http://infinispan.org/docs/stable/server_guide/server_guide.html).
Hibernate OGM allows to work with infinispan remote caches using the known JPA interface.
With the help of Hibernate Search we can easily add lucene indexes to the cache fields.

## Involved components

We need an Infinispan Remote Server and a Wildfly Application Server containing static modules of Hibernate ogm and relative versions of Hibernate ORM, Hibernate Search and Infinispan (Hot Rod) client.
Once the Hibernate OGM version is choose, all other versions will be established:

| Hibernate OGM      | Hibernate ORM    | Hibernate Search | Infinispan Client/Server  |
| ------------------ |:----------------:|:----------------:|:-------------------------:|
| 5.2.0.Alpha1       | 5.1.9.Final      | 5.6.2.Final      | 8.2.6.Final               |
| 5.1.0.Final        | 5.1.4.Final      | 5.6.1.Final      | 8.2.5.Final               |

Wildfly __10.1.0.Final__ version is on the list of tested versions for Hibernate OGM 5.x versions.
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

## REST Interface

### Club Resource

#### Get all club
Returns the bulk get of all sport clubs.

```
http://localhost:8080/playsport-demo/club
@GET
```

#### Create club
Creates a new sport club.
```
@POST
http://localhost:8080/playsport-demo/club
```
Example payload:
```json
{
	"name" : "Roma",
	"code" : "RM731",
	"taxCode" : "TAX-RM731RM731",
	"vatNumber" : "VAT-RM731RM731"
}
```

### Athlete Resource

#### Get all athletes
Returns the bulk get of all athletes.
```
@GET
http://localhost:8080/playsport-demo/athlete
```

#### Create athlete
Creates a new athlete.
```
@POST
http://localhost:8080/playsport-demo/athlete
```
Example payload:
```json
{
	"uispCode" : "123456789",
	"name" : "Fabio Massimo",
	"surname" : "Ercoli",
	"taxCode" : "J39d39JD3",
	"email" : "f.ciao@gmail.com",
	"hometown" : "Rome",
	"birthDate" : 312031342690,
	"address" : {
		"state" : "Italy",
		"city" : "Rome",
		"street" : "via mario iv",
		"zip" : "01921"
	}
}
```

#### Join club
Link an existing athlete to an existing sport club.

__${athleteUispCode}__ is the athlete Uisp Code.
__${clubCode}__ is the sport club code.

```
@PUT
http://localhost:8080/playsport-demo/athlete/uispCode/${athleteUispCode}/clubCode/${clubCode}
```





