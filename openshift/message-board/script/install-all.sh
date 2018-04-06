#!/usr/bin/env bash
set -e

# Install templates and image stream
# Infinispan template taken from here: https://github.com/infinispan/infinispan-openshift-templates
oc create -f ../template/infinispan-persistent.json
oc import-image my-jboss-eap-7/eap71-openshift --from=registry.access.redhat.com/jboss-eap-7/eap71-openshift --confirm
oc import-image my-rhscl/nginx-112-rhel7 --from=registry.access.redhat.com/rhscl/nginx-112-rhel7 --confirm

# Install Infinispan Server
oc new-app --template=infinispan-persistent
oc delete route infinispan-persistent-app-management

# Install MySql Server
oc new-app --template=mysql-persistent -p DATABASE_SERVICE_NAME=account-mysql -p MYSQL_USER=messageboard -p MYSQL_PASSWORD=redhat -p MYSQL_ROOT_PASSWORD=hibernate MYSQL_DATABASE=account --name=account-mysql

# Compile && Test
mvn -f ../pom.xml clean install

# Create binary build distribution
sh ./create-binary-build-directory.sh

# Install Web App
oc new-app --image-stream=nginx-112-rhel7:latest~./nocontent --name=message-board-web
oc start-build message-board-web --from-dir=./message-board-web --follow
oc expose svc/message-board-web --name=web

# Install Account MicroService
oc new-app --image-stream=eap71-openshift:latest~./nocontent -e DB_SERVICE_PREFIX_MAPPING="account-mysql=MYSQL" -e MYSQL_USERNAME=messageboard -e MYSQL_PASSWORD=redhat -e MYSQL_DATABASE=account -e OPENSHIFT_KUBE_PING_NAMESPACE=account --name=account-service
oc start-build account-service --from-dir=./account-service --follow

# Install Message MicroService
oc new-app --image-stream=eap71-openshift:latest~./nocontent -e OPENSHIFT_KUBE_PING_NAMESPACE=message --name=message-service
oc start-build message-service --from-dir=./message-service --follow

# Setting liveness probes
oc set probe dc/account-service --liveness --failure-threshold 3 --initial-delay-seconds 30 --get-url=http://:8080/account-service/health
oc set probe dc/message-service --liveness --failure-threshold 3 --initial-delay-seconds 30 --get-url=http://:8080/message-service/health

# Setting readiness probes
oc set probe dc/account-service --readiness --failure-threshold 3 --initial-delay-seconds 30 --get-url=http://:8080/account-service/health
oc set probe dc/message-service --readiness --failure-threshold 3 --initial-delay-seconds 30 --get-url=http://:8080/message-service/health

oc get pods -w