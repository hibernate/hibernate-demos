# Setting up the environment

## Database

Install postgresql and configure it.

* One-liner with docker:
  ```
  sudo docker run --name postgresql-hibernate_demo -e POSTGRES_PASSWORD=hibernate_demo -e POSTGRES_USER=hibernate_demo -e POSTGRES_DB=hsearch_es_wikipedia -p 5432:5432 -d postgres:11.1
  ```
* Otherwise, for Fedora, [see here](https://fedoraproject.org/wiki/PostgreSQL)).
You will need to enable the `md5` authentication scheme for the local network connection,
and to create a `hibernate_demo` user with the password `hibernate_demo`.

## Data

You can try a fully automated initialization using this command:

```
./src/init/init -d postgresql-hibernate_demo
```

Or, if not using docker:

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
./src/init/init <the uncompressed dump>
```

The script will ask for a password: just use `hibernate_demo`.

## Elasticsearch

Ensure you have an Elasticsearch 5 instance running and accessible from <http://localhost:9200/>:

* One-liner using temporary storage with docker (ES6, but it should work for this demo):
  ```
  sudo docker run --ulimit memlock=-1:-1 -ti --tmpfs /run --tmpfs=/opt/elasticsearch/volatile/data:uid=1000 --tmpfs=/opt/elasticsearch/volatile/logs:uid=1000 -p 9200:9200 -p 9300:9300 --name es-it sanne/elasticsearch-light-testing
  ```
* Otherwise, you can download Elasticsearch from here: <https://www.elastic.co/downloads/elasticsearch>.
Unzip the downloaded file, and just run `bin/elasticsearch` to start an instance on <http://localhost:9200>.

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

Finally, you can retrieve a page with:

```
curl http://localhost:8080/page/<page ID>/
```

## Other implementations

Alternative implementations of the search are demonstrated in previous commit.

See the git history and check out the relevant commit to see those implementations in action:

 * Naïve SQL implementation with `ILIKE`
 * Embedded Lucene implementation 

