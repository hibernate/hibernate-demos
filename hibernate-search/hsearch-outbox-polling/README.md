# hsearch-outbox-polling

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```shell script
./mvnw compile quarkus:dev
```

You can then see the indexed entity types there, and trigger mass indexing:

http://localhost:8080/q/dev-ui/io.quarkus.quarkus-hibernate-search-orm-elasticsearch/indexed-entity-types

And you can trigger the various endpoints (CRUD, search, ...) through Swagger UI:

http://0.0.0.0:9000/q/swagger-ui/

## Running the application in prod mode

This will build a container and run it, along with all the necessary services (PostgreSQL, Elasticsearch, ...):

```shell script
./mvnw clean package
podman compose up
# OR
docker compose up
```

As in dev mode, you can trigger the various endpoints (CRUD, search, ...) through Swagger UI:

http://0.0.0.0:9000/q/swagger-ui/

You can also easily add additional instances of the application to the "cluster":

```shell script
podman run --rm -it \
    --net hsearch-outbox-polling_pgnet,hsearch-outbox-polling_esnet \
    -e QUARKUS_DATASOURCE_JDBC_URL=jdbc:postgresql://pg01:5432/hibernate_demo \
    -e QUARKUS_DATASOURCE_USERNAME=hibernate_demo \
    -e QUARKUS_DATASOURCE_PASSWORD=hibernate_demo \
    -e QUARKUS_HIBERNATE_SEARCH_ORM_ELASTICSEARCH_HOSTS=es01:9200,es02:9200 \
    hibernate-demo/hsearch-outbox-polling:1.0.0-SNAPSHOT
```

After adding an instance,
watch the logs of each application for hints of rebalancing,
and see when you  how listener
and observe rebalancing, as well as distribution of the next listener-triggered indexing, in logs:

## Examples

### Basic CRUD

1. Start the application in dev mode with `./mvnw compile quarkus:dev`,
   or in prod mode with `docker-compose up`. 
2. Go to http://localhost:8080/q/swagger-ui/
3. Use the endpoints `/author`, `/author/{id}`, `/book`, `/book/{id}`.

You will see the resulting outbox event processing
and Elasticsearch indexing requests in the application logs.

### Full-text search

1. Go to http://localhost:8080/q/swagger-ui/
2. Use the endpoints `/book/search` and `/book/search/facets`.

### Mass indexing

1. Start the application in dev mode with `./mvnw compile quarkus:dev`,
   or in prod mode with `docker-compose up`.
2. Run `curl -s -XPOST 'localhost:9000/admin/reindex'`

Alternatively, in dev mode only:

* Mass indexing, happens automatically on startup.
* To trigger it manually, you can also use the Dev UI:
  1. Go to http://localhost:8080/q/dev-ui/io.quarkus.quarkus-hibernate-search-orm-elasticsearch/indexed-entity-types
  2. Select the entity types to reindex and click "Reindex selected"

## Creating a native executable

You can create a native executable using: 
```shell script
./mvnw package -Pnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using: 
```shell script
./mvnw package -Pnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./target/hsearch-outbox-polling-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult https://quarkus.io/guides/maven-tooling.

## Related Guides

- Hibernate ORM with Panache ([guide](https://quarkus.io/guides/hibernate-orm-panache)): Simplify your persistence code for Hibernate ORM via the active record or the repository pattern
- SmallRye Health ([guide](https://quarkus.io/guides/smallrye-health)): Monitor service health
- Hibernate Search + Elasticsearch ([guide](https://quarkus.io/guides/hibernate-search-orm-elasticsearch)): Automatically index your Hibernate entities in Elasticsearch
- SmallRye OpenAPI ([guide](https://quarkus.io/guides/openapi-swaggerui)): Document your REST APIs with OpenAPI - comes with Swagger UI
- RESTEasy Reactive ([guide](https://quarkus.io/guides/resteasy-reactive)): A Jakarta REST implementation utilizing build time processing and Vert.x. This extension is not compatible with the quarkus-resteasy extension, or any of the extensions that depend on it.
- JDBC Driver - PostgreSQL ([guide](https://quarkus.io/guides/datasource)): Connect to the PostgreSQL database via JDBC
