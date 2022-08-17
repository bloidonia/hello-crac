#!/bin/bash
set -e

# build the layers for the application
./gradlew buildLayers

# Copy the warmup script into the build
cp docker/warmup.sh build/docker/main

# Create our checkpoint image
docker build \
    -f docker/CheckpointImage.docker \
    --tag checkpoint \
    build/docker/main

# Run the checkpoint image
docker run \
  --privileged \
  --rm \
  --name crac-checkpoint \
  -v $(pwd)/build/docker/main/layers/cr:/home/app/cr \
  -p 8080:8080 \
  checkpoint

