#!/usr/bin/env bash

set -eu

if [ -f .env ]; then
  source .env
fi

git pull
./gradlew bootRun
