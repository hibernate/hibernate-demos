#!/usr/bin/env bash
set -e
# Account MicroService

# Compile && Test
mvn -f ../account-service/pom.xml clean install

# Update war
cp -f ../account-service/target/account-service.war ./account-service

# New build on OCP
oc start-build account-service --from-dir=./account-service --follow