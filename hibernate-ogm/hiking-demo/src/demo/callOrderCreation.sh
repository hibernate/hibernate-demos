#!/bin/sh
# Released under ASL 2.0, see project root

# Assumes the script is run from its home dir
# assumes HTTPie is present
echo "http POST http://localhost:8080/hibernate-ogm-hiking-demo-1.0-SNAPSHOT/hiking-manager/orders/ < orderCreation.json"
echo "\n"
cat orderCreation.json
http POST http://localhost:8080/hibernate-ogm-hiking-demo-1.0-SNAPSHOT/hiking-manager/orders/ < orderCreation.json
