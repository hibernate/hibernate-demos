# Setting up the environment

## Services

Install docker and docker-compose, then run this from the root of the project:

```
docker-compose -f environment-stack.yml -p hsearch-elasticsearch-wikipedia up
```

You can later remove the created services and volumes with this command:

```
docker-compose -f environment-stack.yml -p hsearch-elasticsearch-wikipedia down -v
```

## Data

You can try a fully automated initialization using this command:

```
./src/init/init
```

If this succeeds, you're all set, you can go to the next step.

Otherwise, Wikipedia's dumps page structure probably changed, but you can proceed as explained below.

Retrieve a dataset of current pages (one of those described as "All pages, current versions only.")
from [Wikipedia's dumps page](https://dumps.wikimedia.org/enwiki/) (or a [mirror](https://dumps.wikimedia.org/mirrors.html)).

Example: `enwiki-20170220-pages-meta-current1.xml-p000000010p000030303.bz2`

Extract the dump:

```
bunzip2 <the dump>
```

Initialize the database using the provided script:

```
./src/init/init -f <path to the uncompressed dump>
```

# Running the project

You must have [Maven (`mvn`)](https://maven.apache.org/) in your path, version 3.2 or later.
You must use Java 8.

Execute the following command:

```
mvn clean spring-boot:run
```

You can trigger reindexing with:

```
curl -XPOST http://localhost:8080/admin/reindex
```

The progress will be updated in the application logs.

Then you can query the index with:

```
curl http://localhost:8080/page/search/?q=<sometext>
```

For instance:

```
curl http://localhost:8080/page/search/?q=car
```

You can also select the sort (either `RELEVANCE` or `TITLE`):

```
curl http://localhost:8080/page/search/?q=car&s=TITLE
```

Finally, you can retrieve a Wikipedia page with:

```
curl http://localhost:8080/page/<page ID>/
```

## Other implementations

Alternative implementations of the search are demonstrated in previous commit.

See the git history and check out the relevant commit to see those implementations in action:

 * Naïve SQL implementation with `ILIKE`
 * Embedded Lucene implementation 

