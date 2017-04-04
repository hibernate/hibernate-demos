# Updating Hibernate Validator in WildFly 10

This project shows how to use the patch file provided by Hibernate Validator to
update a given instance of WildFly to the latest Hibernate Validator and Bean
Validation modules.

It accompanies the blog post [Using Bean Validation 2.0 on WildFly 10](http://in.relation.to/2017/04/04/testing-bean-validation-2-0-on-wildfly-10/).

## Building the project

Execute the following command to build the project:

    mvn clean install

This will download WildFly 10.1, the Hibernate Validator 6 patch file, apply the
patch to the server and run a simple integration test using Bean Validation 2.0
functionality via Arquillian.
