#!/bin/bash
set -e

# build the layers for the application
./gradlew buildLayers

# Copy the warmup script into the build
cp docker/warmup.sh build/docker/main
rm -rf build/docker/main/layers/cr

# Create our checkpoint image
docker build \
  -f docker/CheckpointImage.docker \
  --tag checkpoint \
  build/docker/main

# Run the checkpoint image
docker run \
  --platform linux/amd64 \
  --privileged \
  --rm \
  --name crac-checkpoint \
  -v $(pwd)/build/docker/main/layers/cr:/home/app/cr \
  -p 8080:8080 \
  checkpoint

# Build our runtime image
docker build \
  -f docker/RuntimeImage.docker \
  --tag hello-crac:1.0 \
  build/docker/main

# Run the runtime image
docker run \
  --privileged \
  -p 8080:8080 \
  hello-crac:1.0
