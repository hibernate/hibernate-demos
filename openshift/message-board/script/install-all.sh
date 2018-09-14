#!/usr/bin/env bash
set -x
set -e

# Copy bundles for the binary builds
sh ./create-binary-build-directory.sh

# Install templates and image stream
# Infinispan template taken from here: https://github.com/infinispan/infinispan-openshift-templates
oc create -f ../template/infinispan-persistent.json
oc import-image openshift/wildfly-130-centos7 --confirm

# Install Infinispan Server
CUSTOM_INFINISPAN_IMAGE=`cat CUSTOM_INFINISPAN_IMAGE`
oc new-app --template=infinispan-persistent -p IMAGE="$CUSTOM_INFINISPAN_IMAGE"
oc delete route infinispan-persistent-app-management

# Install MySql Server
oc new-app --template=mysql-persistent -p DATABASE_SERVICE_NAME=mysql -p MYSQL_USER=messageboard -p MYSQL_PASSWORD=redhat -p MYSQL_ROOT_PASSWORD=hibernate MYSQL_DATABASE=account --name=mysql

# Install Account MicroService
oc new-app --image-stream=wildfly-130-centos7~./nocontent -e MYSQL_USER=messageboard -e MYSQL_PASSWORD=redhat -e MYSQL_DATABASE=account -e OPENSHIFT_KUBE_PING_NAMESPACE=account --name=account-service
oc start-build account-service --from-dir=./account-service

# Install Message MicroService
oc new-app --image-stream=wildfly-130-centos7~./nocontent -e OPENSHIFT_KUBE_PING_NAMESPACE=message --name=message-service
oc start-build message-service --from-dir=./message-service

# Install Web App
oc new-app --image-stream=nginx~./nocontent --name=message-board-web
oc start-build message-board-web --from-dir=./message-board-web
oc expose svc/message-board-web --name=web

# Setting liveness probes
oc set probe dc/account-service --liveness --failure-threshold 3 --initial-delay-seconds 30 --get-url=http://:8080/account-service/health
oc set probe dc/message-service --liveness --failure-threshold 3 --initial-delay-seconds 30 --get-url=http://:8080/message-service/health

# Setting readiness probes
oc set probe dc/account-service --readiness --failure-threshold 3 --initial-delay-seconds 30 --get-url=http://:8080/account-service/health
oc set probe dc/message-service --readiness --failure-threshold 3 --initial-delay-seconds 30 --get-url=http://:8080/message-service/health

echo "Insatallation finished!"
