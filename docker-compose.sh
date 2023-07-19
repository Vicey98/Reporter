#!/bin/bash

echo "Removing all containers"
docker container rn -f $(docker container ls -aq)

set -Eeuo pipefail

echo "Building reporting jar"
mvn clean package spring-boot:repackage -DskipTests -Dmaven.javadoc.skip=true

echo "Building reporting docker image"
docker build . -t reporter:latest -f Dockerfile.local

echo "Starting docker compose"
docker compose build && docker compose -f docker-compose.yml up

