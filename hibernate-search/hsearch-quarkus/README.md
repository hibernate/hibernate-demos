# `hsearch-quarkus`

A demonstration application using Hibernate Search within Quarkus.

Related blog post: https://in.relation.to/2019/11/12/hibernate-search-quarkus/

## Building and running the application in development mode

### Setting up the execution environment

Install docker and docker-compose, then run this from the root of the project:

```
docker-compose -f environment-stack.yml -p hsearch-quarkus-env up
```

You can later remove the created services and volumes with this command:

```
docker-compose -f environment-stack.yml -p hsearch-quarkus-env down -v
```

### Starting the application in development mode

Execute the following command:

```
./mvnw compile quarkus:dev
```

## Building and running the application as a native binary in a container

Build the native binary with:

```
./mvnw package -Pnative
```

Then build the container containing the native binary with:

```
docker build -f src/main/docker/Dockerfile.native -t quarkus/hsearch-quarkus .
```

Then run the container along with PostgreSQL and Elasticsearch with:

```
docker-compose -f hsearch-quarkus.yml -p hsearch-quarkus up
```

You can later remove the created services and volumes with this command:

```
docker-compose -f hsearch-quarkus.yml -p hsearch-quarkus down -v
```

## Using the application

The REST service will be available on port 8080.
See `ClientResource.java` for the API.

Use `curl` to access the service:

```
curl -X <GET or DELETE> http://localhost:8080/<path>
```

Or:

```
curl -X <POST or PUT> http://localhost:8080/<path> -H "Content-Type: application/json" -d '<your json>'
```
