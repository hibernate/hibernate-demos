# Setting up the environment

## Database

Install postgresql and configure it ([see here for Fedora](https://fedoraproject.org/wiki/PostgreSQL)).

Create a database and user:

```
# From a shell
sudo -i -u postgres
createuser -U postgres -P hibernate_demo # When prompted, use "hibernate_demo" as a password
createdb -U postgres -O hibernate_demo hsearch_es_wikipedia
```

You need to enable the `md5` authentication scheme for the local network connection.

So, if you just installed PostgreSQL, you should make your `/var/lib/pgsql/data/pg_hba.conf` file look like:
```
local   all             all                                     peer
host    all             all             127.0.0.1/32            md5
host    all             all             ::1/128                 md5
```
(just change `ident` to `md5` in the existing lines)

Don't forget to reload your PostgreSQL server after this change:
```
sudo systemctl reload postgresql
```

## Data

You can try a fully automated initialization using this command:

```
./src/init/init
```

The script will ask for a password: just use `hibernate_demo`.

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

