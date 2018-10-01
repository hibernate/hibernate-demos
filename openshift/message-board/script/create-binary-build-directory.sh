#!/usr/bin/env bash
rm -fr account-service
rm -fr message-service
rm -fr nocontent
mkdir account-service
mkdir message-service
mkdir message-service/configuration
mkdir nocontent
cp ../account-service/target/account-service.war ./account-service
cp ../message-service/target/message-service.war ./message-service
cp -r ../message-service/target/wildfly-13.0.0.Final/modules ./message-service/modules
rm -fr ./message-service/modules/system
cp -r ../message-service/target/wildfly-13.0.0.Final/modules/system/layers/base/org/hibernate/5.3 ./message-service/modules/org/hibernate/5.3
cp -r ../message-service/target/wildfly-13.0.0.Final/modules/system/layers/base/org/hibernate/envers ./message-service/modules/org/hibernate/envers
cp -r ../message-service/target/wildfly-13.0.0.Final/modules/system/layers/base/org/hibernate/orm ./message-service/modules/org/hibernate/orm
cp -r ../message-service/target/wildfly-13.0.0.Final/modules/system/add-ons/ispn/* ./message-service/modules/
mkdir -p ./message-service/modules/net/bytebuddy
cp -r ../message-service/target/wildfly-13.0.0.Final/modules/system/layers/base/net/bytebuddy/1.8.17 ./message-service/modules/net/bytebuddy/1.8.17
cp ../message-service/target/wildfly-13.0.0.Final/standalone/configuration/standalone-ee8.xml ./message-service/configuration/standalone.xml
cp -f ../extra/config/nginx.conf ./message-board-web
