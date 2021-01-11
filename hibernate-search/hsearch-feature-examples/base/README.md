# hsearch-feature-examples project

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

## Setting up the environment

Install docker and docker-compose, then run this from the root of the project:

```shell script
docker-compose -f ../environment-stack.yml -p hsearch-feature-examples up
```

You can later remove the created services and volumes with this command:

```shell script
docker-compose -f ../environment-stack.yml -p hsearch-feature-examples down -v
```

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```shell script
./mvnw compile quarkus:dev
```

## Examples

You will need to install `curl` (HTTP requests) and `jq` (JSON parsing and highlighting).

### Basic CRUD

See `TShirtService.java`.

Listing all entities:

```shell script
curl -s -XGET -H 'Content-Type: application/json' 'localhost:8080/tshirt/' | jq
```

`PUT`, `POST` (needs ID), `DELETE` (needs ID) also available.

## Packaging and running the application

The application can be packaged using:
```shell script
./mvnw package
```
It produces the `hsearch-feature-examples-1.0.0-SNAPSHOT-runner.jar` file in the `/target` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/lib` directory.

If you want to build an _über-jar_, execute the following command:
```shell script
./mvnw package -Dquarkus.package.type=uber-jar
```

The application is now runnable using `java -jar target/hsearch-feature-examples-1.0.0-SNAPSHOT-runner.jar`.

## Creating a native executable

You can create a native executable using: 
```shell script
./mvnw package -Pnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using: 
```shell script
./mvnw package -Pnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./target/hsearch-feature-examples-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult https://quarkus.io/guides/maven-tooling.html.
