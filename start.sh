#!/bin/sh

set -e

docker-compose -f dep/docker-compose.yml up -d
./gradlew build run
