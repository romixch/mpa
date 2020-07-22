#!/usr/bin/env bash

# Build the native image binary
./gradlew build -Dquarkus.package.type=native -Dquarkus.native.container-build=true

# Build a docker image with the native image
docker build -f src/main/docker/Dockerfile.native -t romixch/ch.romix.mpa .

docker tag romixch/ch.romix.mpa gcr.io/progressive-enhancement/ch.romix.mpa

# gcloud auth configure-docker

docker push gcr.io/progressive-enhancement/ch.romix.mpa

gcloud run deploy chromixprogressiveenhancement --project=progressive-enhancement --region=us-west1 --image=gcr.io/progressive-enhancement/ch.romix.mpa --platform=managed
