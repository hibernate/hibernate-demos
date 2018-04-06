#!/usr/bin/env bash
set -e
# Web Application

# Clear build folder
rm -fr ./message-board-web

# Compile & build Angular distribution
cd ../message-board-web
sh ./build.sh
cd ../script

# Update Nginx proxy conf
cp -f ../config/nginx.conf ./message-board-web

# New build on OCP
oc start-build message-board-web --from-dir=./message-board-web --follow