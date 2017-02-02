# Hibernate ORM OSGi Tutorial: Unmanaged JPA

This tutorial demonstrates the use of Hibernate's "unmanaged JPA" configuration in your OSGi container.  Unlike
"managed JPA", which uses Enterprise OSGi JPA (ie, Apache Aries JPA) to manage the EntityManagerFactory, the client
bundle is instead in charge of creating it manually.

This tutorial also demonstrates how to register custom impls of Hibernate ORM extension points, registered through the
use of OSGi Blueprint (blueprint.xml).

## Running the Tutorial

To run the tutorial, use the following commands in the Karaf console:

- feature:repo-add mvn:org.hibernate/hibernate-osgi/[VERSION]/xml/karaf
- feature:install hibernate-orm
- feature:repo-add file:/[PATH]/hibernate-demos/hibernate-orm/osgi/unmanaged-jpa/features.xml
- feature:install hibernate-osgi-unmanaged-jpa