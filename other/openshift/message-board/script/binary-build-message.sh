#!/usr/bin/env bash
set -e
# Message MicroService

# Compile && Test
mvn -f ../message-service/pom.xml clean install

# Update war
cp -f ../message-service/target/message-service.war ./message-service

# New build on OCP
oc start-build message-service --from-dir=./message-service --follow