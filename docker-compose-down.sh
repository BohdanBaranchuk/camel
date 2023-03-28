#!/bin/sh

docker-compose -f docker-compose.yml down
docker container prune --force
docker image prune --force
