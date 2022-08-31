#!/usr/bin/env bash

execute() {
    curl --silent --retry 200 --retry-delay 0 --retry-all-errors http://localhost:8080/hello/tim
}

CONTAINER=$(docker run -d --rm -p 8080:8080 --privileged $1)
time execute
docker container kill $CONTAINER