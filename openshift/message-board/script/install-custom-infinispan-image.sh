# Creates custom Infinispan image and deploy it to OpenShift docker registry,
# this code is inspired by the project https://github.com/jboss-dockerfiles/infinispan

#!/usr/bin/env bash
set -x
set -e

# add developer the ability to push a image
oc login -u system:admin
oc adm policy add-role-to-user system:registry developer
oc adm policy add-role-to-user admin developer -n myproject
oc adm policy add-role-to-user system:image-builder developer

# take OpenShift Docker registry IP
oc project default
REGISTRY_IP=`oc get svc/docker-registry -o yaml | grep clusterIP: | awk '{print $2}'`

# build image
IMAGE="${REGISTRY_IP}:5000/myproject/infinispan-with-board-task"
docker build --no-cache --force-rm -t $IMAGE ../extra/message-server-task

# push the image from local Docker repo to the OCP Docker repo
# we need a regular user to login to the OpenShift Docker registry
oc login -u developer -p developer
docker login -u $(oc whoami) -p $(oc whoami -t) ${REGISTRY_IP}:5000
docker push ${IMAGE}

# store the image reference to file
# it will be used by the install-all script
rm -fv ./CUSTOM_INFINISPAN_IMAGE
echo "$IMAGE" >> CUSTOM_INFINISPAN_IMAGE
