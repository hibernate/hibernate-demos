# Java time Duration Validator Example

This is a demo project used in the examples for the blog post
 [Adding custom constraint definitions via the Java service loader](http://in.relation.to/2017/03/02/adding-custom-constraint-definitions-via-the-java-service-loader/).
 It contains a constraint annotation `@DurationMin` and corresponding validator for `java.time.Duration`.

## Building the project

Execute the following command to build the project:

    mvn clean install

This will execute some simple JUnit tests and package a jar file with constraint
validators implemented in this demo project.
