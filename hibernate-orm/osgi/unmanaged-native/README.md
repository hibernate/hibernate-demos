# Hibernate ORM OSGi Tutorial: Unmanaged Native

This tutorial demonstrates the use of Hibernate's "unmanaged native" configuration in your OSGi container.  In this
configuration, the client bundle is in charge of manually creating the Hibernate SessionFactory, instead of using JPA.

This tutorial also demonstrates how to register custom impls of Hibernate ORM extension points, registered through the
use of OSGi Blueprint (blueprint.xml).

## Running the Tutorial

To run the tutorial, use the following commands in the Karaf console:

- feature:repo-add mvn:org.hibernate/hibernate-osgi/[VERSION]/xml/karaf
- feature:install hibernate-orm
- feature:repo-add file:/[PATH]/hibernate-demos/hibernate-orm/osgi/unmanaged-native/features.xml
- feature:install hibernate-osgi-unmanaged-native