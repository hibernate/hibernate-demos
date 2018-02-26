# hibernate-demos

This repository contains demos used live during presentations or in blog posts; The following demos are currently available:

* Hibernate ORM
    - _core/Basic_: simple comparison between JDBC, ORM, and JPA
    - _core/Fetching Strategies_: "when" (laziness) and "how" (fetch style)
    - _core/Value Generation_: annotations allowing in-memory and DB generated properties, both for INSERT and INSERT/UPDATE actions
    - _core/Multi-Tenancy_: multiple, concurrent databases and clients from a single Hibernate instance
    - _core/Caching_: entity second level cache (2LC) and query cache
    - _core/Envers_: historical/audited data
    - _core/Spatial_: geographical data
    - _osgi_: tutorials demonstrating all possible ORM OSGi configurations
* Hibernate OGM
    - _hiking-demo_: Demo project used for the talk "Hibernate OGM: Talking to NoSQL in Red Hat JBoss EAP" presented at Red Hat Summit 2014. It shows how to use MongoDB as data store in a Java EE application through JPA / Hibernate OGM.
* Hibernate Search
    - _hsearch-with-elasticsearch_: Shows how to use the Elasticsearch backend new in Hibernate Search 5.6.
      Used for the talk "From Hibernate to Elasticsearch in no Time" at JavaZone 2016.
    - _hsearch-elasticsearch-wikipedia_: Demonstrates a REST service using Hibernate Search 5.8 + Elasticsearch 5 to search into extensive data from Wikipedia.
* Hibernate Validator
    - _threeten-extra-validator-example_: Constraint validators for ThreeTen Extra date/time types.
      Accompanies the blog post http://in.relation.to/2017/03/02/adding-custom-constraint-definitions-via-the-java-service-loader/
    - _time-duration-validator-example_: Custom constraint and validator, retrieved via the service loader.
      Accompanies the blog post http://in.relation.to/2017/03/02/adding-custom-constraint-definitions-via-the-java-service-loader/
    - _updating-hv-in-wildfly_: How to upgrade WildFly 10 to the latest version of Hibernate Validator.
      Accompanies the blog post http://in.relation.to/2017/04/04/testing-bean-validation-2-0-on-wildfly-10/
    - _javafx-validation-example_: Shows usage of Bean Validation 2 with JavaFX
    - _custom-value-extractors_: Shows how to put constraints to custom containers such as Guava's `Multimap`.
      Accompanies the blog post http://in.relation.to/2018/02/26/putting-bean-validation-constraints-to-multimaps/

* Java 9
    - multi-release-jar-demo: Shows how to build multi-release JARs with Java 9.
      Accompanies the blog post http://in.relation.to/2017/02/13/building-multi-release-jars-with-maven/
    - custom-jlink-plugin: Shows how to customize Java 9 modular runtime images with jlink plug-ins. The example shows a plug-in for adding a Jandex annotation index for one or more modules to the runtime image.
* Other
    - wildfly-patch-creation: How to create WildFly patch files.
      Accompanies the blog post http://in.relation.to/2017/05/29/creating-patches-for-wildfly/

## License

If not stated otherwise, the demos are licensed under the Apache License, Version 2.0 (see https://www.apache.org/licenses/LICENSE-2.0). Refer to the headers of individual files for specific license and copyright information, in particular of included library files.
