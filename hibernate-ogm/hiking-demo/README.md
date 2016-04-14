# hibernate-ogm-hiking-demo

This is a demo project used for the talk [Hibernate OGM: Talking to NoSQL in Red Hat JBoss EAP](http://www.redhat.com/summit/sessions/index.html#274) presented at Red Hat Summit 2014. It shows how to use MongoDB as data store in a Java EE application through JPA / Hibernate OGM.

The application allows to manage (create, list, update, delete) hikes. It exposes a REST API using JAX-RS. The web client is built using AngularJS and invokes this API to display/update the data.

## Building the project

Execute the following command to build the project:

    mvn clean install

This will execute some simple JUnit tests which use JPA's RESOURCE_LOCAL mode to access the datastore as well as some integration tests which run on the application server (executed via Arquillian) using the JTA mode. This requires a MongoDB server to be installed and running locally.

By default, the integration tests are executed on the WildFly 8 application server, which is downloaded, unzipped, updated with the required modules for Hibernate OGM and started automatically.

Alternatively, you can execute the integration tests on a locally installed instance of JBoss EAP 6. To do so, get the ZIP archive with the required modules of Hibernate OGM from [here](https://repository.jboss.org/nexus/content/groups/public/org/hibernate/ogm/hibernate-ogm-modules-eap6/) and unzip it into the modules directory of the JBoss EAP installation. Then start the server and run the following command:

    mvn clean install -Peap-remote

## Deploying the application on a local application server

To deploy the application on a local application server (which must have been started before), run the following command for WildFly 8:

    mvn wildfly:deploy -DskipTests=true

And the following to deploy to JBoss EAP:

    mvn jboss-as:deploy -DskipTests=true

In both cases you can access the application at the following URL once the deployment has finished:

    http://localhost:8080/hibernate-ogm-hiking-demo-1.0-SNAPSHOT/hikes.html

You can then create hikes with sections and organizers. You also can search for hikes. Currently the UI does not allow to create persons which can be referenced from hikes. But there is a REST call for this, so you can e.g. use curl to create persons as follows:

    curl -X POST -d '{"name":"Bob"}' -H "Accept: application/json" -H "Content-type: application/json" http://localhost:8080/hibernate-ogm-hiking-demo-1.0-SNAPSHOT/hiking-manager/persons

## Deploying the application on OpenShift

The demo is prepared to be deployed and run on Red Hat's platform-as-a-service, OpenShift.

This requires an OpenShift application to be set up with the JBoss EAP 6 cartridge and the MongoDB cartridge. Refer to the official documentation for instructions how to set up an application. You also need to add the application's git repository on OpenShift as a remote to your copy of the project.

The project contains the required modules for Hibernate OGM in the _.openshift/config/modules/_ folder; They will be added automatically to the server's module path upon start-up. In order to configure the database credentials, you need to log into your instance via SSH and run the following command once:

    echo "-Dhibernate.ogm.datastore.host=$OPENSHIFT_MONGODB_DB_HOST \
        -Dhibernate.ogm.datastore.port=$OPENSHIFT_MONGODB_DB_PORT \
        -Dhibernate.ogm.datastore.username=$OPENSHIFT_MONGODB_DB_USERNAME \
        -Dhibernate.ogm.datastore.password=$OPENSHIFT_MONGODB_DB_PASSWORD \
        -Dhibernate.ogm.datastore.database=ogm" \
        > .env/user_vars/JAVA_OPTS_EXT

OpenShift makes MongoDB's host, user etc. available via environment variables. This command puts them into the _JAVA\_OPTS\_EXT_ variable which contains the VM arguments passed to the server upon startup. That way Hibernate OGM can access the credentials.

When you then have done a deployment (e.g. by pushing the application code to the OpenShift remote or through rhc), you can access the application in the Cloud at

    http://<%app-name%>-<%domain-name%>.rhcloud.com/hikes.html
    
Any feedback on the demo is highly welcome, just send a mail to the hibernate-dev mailing list.
