# hibernate-demos

This repository contains demos used live during presentations; The following demos are currently available:

* Hibernate ORM
 * _core/Basic_: simple comparison between JDBC, ORM, and JPA
 * _core/Fetching Strategies_: "when" (laziness) and "how" (fetch style)
 * _core/Value Generation_: annotations allowing in-memory and DB generated properties, both for INSERT and INSERT/UPDATE actions
 * _core/Multi-Tenancy_: multiple, concurrent databases and clients from a single Hibernate instance
 * _core/Caching_: entity second level cache (2LC) and query cache
 * _core/Envers_: historical/audited data
 * _core/Spatial_: geographical data
 * _osgi_: tutorials demonstrating all possible ORM OSGi configurations
* Hibernate OGM
 * _hiking-demo_: Demo project used for the talk "Hibernate OGM: Talking to NoSQL in Red Hat JBoss EAP" presented at Red Hat Summit 2014. It shows how to use MongoDB as data store in a Java EE application through JPA / Hibernate OGM.
* Hibernate Search
 * _hsearch-with-elasticsearch_: Shows how to use the Elasticsearch backend new in Hibernate Search 5.6.
   Used for the talk "From Hibernate to Elasticsearch in no Time" at JavaZone 2016.
* Java 9
 * multi-release-jar-demo: Shows how to build multi-release JARs with Java 9.
   Accompanies the blog post http://in.relation.to/2017/02/13/building-multi-release-jars-with-maven/

## License

If not stated otherwise, the demos are licensed under the Apache License, Version 2.0 (see https://www.apache.org/licenses/LICENSE-2.0). Refer to the headers of individual files for specific license and copyright information, in particular of included library files.
