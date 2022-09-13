#!/usr/bin/env bash

execute() {
  while ! curl -s http://localhost:8080/hello/tim; do sleep 0.001; done
}

CONTAINER=$(docker run -d --rm -p 8080:8080 --privileged $1)
time execute
docker container kill $CONTAINER
