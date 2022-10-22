#!/usr/bin/env bash

#sdk use java 17.0.4-amzn
#./gradlew clean build
docker build -t seedin_be .
docker tag seedin_be sotcsa/seedin_be
docker push sotcsa/seedin_be
