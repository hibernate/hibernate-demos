# Java time Duration Validator Example

This is a demo project used in the examples for the blog post
 [Using ServiceLoader to add custom constraint definitions](http://need-some-valid-in.relation.to-url).
 It contains a constraint annotation `@DurationMin` and corresponding validator for `java.time.Duration`.

## Building the project

Execute the following command to build the project:

    mvn clean install

This will execute some simple JUnit tests and package a jar file with constraint 
validators implemented in this demo project.
