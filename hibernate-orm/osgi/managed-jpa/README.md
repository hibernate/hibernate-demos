# Hibernate ORM OSGi Tutorial: Managed JPA through Enterprise OSGi JPA

This tutorial demonstrates the use of Enterprise OSGi JPA to create a "managed JPA" environment in your OSGi container.
More specifically, this is based on Apache Karaf 3.0.4 and Apache Aries JPA.  See the Hibernate ORM User Guide's OSGi
chapter for more information!

## Running the Tutorial

To run the tutorial, use the following commands in the Karaf console:

- feature:repo-add mvn:org.hibernate/hibernate-osgi/[VERSION]/xml/karaf
- feature:install hibernate-orm
- feature:repo-add file:/[PATH]/hibernate-demos/hibernate-orm/osgi/managed-jpa/features.xml
- feature:install hibernate-osgi-managed-jpa

IMPORTANT: You'll also need to update the following path within this demo's features.xml:

\<bundle\>blueprint:file:/[PATH]/datasource-h2.xml\</bundle\>

Note that this project is not deployed in Hibernate's Nexus group, so it must be built and installed to your local
repo.  Then take a look at Karaf's etc/org.ops4j.pax.url.mvn.cfg local Maven settings, in order for it to find the
artifacts in the local repo.
