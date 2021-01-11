# hsearch-feature-examples

## Setting up the environment

Install docker and docker-compose, then run this from the root of the project:

```
docker-compose -f environment-stack.yml -p hsearch-feature-examples up
```

You can later remove the created services and volumes with this command:

```
docker-compose -f environment-stack.yml -p hsearch-feature-examples down -v
```

## Running the application

See the README inside the following three sub-directories, each with a different version of the application:

* `base`: basic ORM-based CRUD application.
* `search`: same as `base`, with the addition of Hibernate Search annotation and configuration.
* `search-advanced`: same as `search`, with more advanced examples.
