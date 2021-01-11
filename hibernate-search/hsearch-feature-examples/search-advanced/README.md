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

### Full-text search

See `TShirtService.java`.

```shell script
curl -s -XGET -H 'Content-Type: application/json' 'localhost:8080/tshirt/search?brief=true&q=bike' | jq
curl -s -XGET -H 'Content-Type: application/json' 'localhost:8080/tshirt/search?brief=false&q=bike' | jq
```

### Mass indexing

See `AdminService.java`.

Mass indexing, for this demo application, happens automatically on startup.

To trigger it manually:

```shell script
curl -s -XPOST -H "Content-Type: application/json" 'localhost:8080/admin/reindex'
```

See the application logs for the result.

### Automatic indexing

Creating an entity and seeing the reindexing:

```shell script
curl -s -XPUT -H 'Content-Type: application/json' 'localhost:8080/tshirt/' -d'{"name": "Jumping to the stars!", "collection": 4, "variants": [{"size": "L", "color": "Blue", "price": 4.99}]}}' | jq
# Wait for Elasticsearch to refresh (~1 second), the see the reindexed data:
curl -s -XGET -H 'Content-Type: application/json' 'localhost:8080/tshirt/search?brief=true&q=jump' | jq
```

Updating an indexed-embedded entity and seeing the reindexing:

```shell script
# Search before (no result):
curl -s -XGET -H 'Content-Type: application/json' 'localhost:8080/tshirt/search?brief=true&q=bicycle' | jq
# Show collection:
curl -s -XGET -H 'Content-Type: application/json' 'localhost:8080/collection/2' | jq
# Update collection:
curl -s -XPOST -H 'Content-Type: application/json' 'localhost:8080/collection/2' -d'{"year": 2019, "season": "SPRING_SUMMER", "keywords": "bike, sport, bicycle"}}' | jq
# Wait for Elasticsearch to refresh (~1 second), the see the reindexed data:
curl -s -XGET -H 'Content-Type: application/json' 'localhost:8080/tshirt/search?brief=true&q=bicycle' | jq
```

### Advanced analysis (autocomplete)

See `TShirtService.java`.

```shell script
while read TEXT; do curl -s -XGET -H 'Content-Type: application/json' 'localhost:8080/tshirt/autocomplete' -G --data-urlencode "terms=$TEXT" | jq ; done
# Then type whatever you want, followed by <ENTER>
ju
ju sk
```

Same, without hitting the database:

```shell script
while read TEXT; do curl -s -XGET -H 'Content-Type: application/json' 'localhost:8080/tshirt/autocomplete_nodb' -G --data-urlencode "terms=$TEXT" | jq ; done
# Then type whatever you want, followed by <ENTER>
ju
ju sk
```

### Faceting and nested documents

See `TShirtService.java`.

```shell script
curl -s -XGET -H 'Content-Type: application/json' 'localhost:8080/tshirt/search_facets?brief=true' | jq
curl -s -XGET -H 'Content-Type: application/json' 'localhost:8080/tshirt/search_facets?brief=true&size=XL' | jq
curl -s -XGET -H 'Content-Type: application/json' 'localhost:8080/tshirt/search_facets?brief=false&color=grey' | jq
curl -s -XGET -H 'Content-Type: application/json' 'localhost:8080/tshirt/search_facets?brief=true&size=XL&color=grey' | jq
```

### Mixing native constructs with the DSL

See `TShirtService.java`.

Native aggregation:

```shell script
curl -s -XGET -H 'Content-Type: application/json' 'localhost:8080/tshirt/pricestats?q=jump' | jq
```

Suggest (dealing with JSON request/response):

```shell script
while read TEXT; do curl -s -XGET -H 'Content-Type: application/json' 'localhost:8080/tshirt/suggest' -G --data-urlencode "terms=$TEXT" | jq ; done
# Then type whatever you want, followed by <ENTER>
montain
juml into
operture scienwe
```

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
