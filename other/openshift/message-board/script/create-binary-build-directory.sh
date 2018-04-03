#!/usr/bin/env bash
rm -fr account-service
rm -fr message-service
rm -fr nocontent
mkdir account-service
mkdir message-service
mkdir nocontent
cp ../account-service/target/account-service.war ./account-service
cp ../message-service/target/message-service.war ./message-service
cp -r ../message-service/target/wildfly-11.0.0.Final/modules ./message-service/modules
rm -fr ./message-service/modules/system
cp -f ../config/nginx.conf ./message-board-web
