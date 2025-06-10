#!/bin/bash
set -e

if [ -z "$1" ]; then
  echo "[ERROR] You must provide the JAVA_HOME path as the first argument."
  echo "Usage: $0 <JAVA_HOME_PATH>"
  exit 1
fi

JAVA_HOME="$1"

echo "[INFO] Compiling the project with the portable Maven Wrapper (mvnw) using JAVA_HOME: $JAVA_HOME ..."
JAVA_HOME="$JAVA_HOME" ./mvnw clean package -DskipTests

echo "[INFO] Building the Docker image..."
docker build -f ./docker/application/Dockerfile -t backend-docker-image .

echo "[INFO] Removing previous container (if exists)..."
docker rm -f backend-docker-container || true

echo "[INFO] Starting the Docker container with 'docker' profile..."
docker run -d --name backend-docker-container -p 8080:8080 -e SPRING_PROFILES_ACTIVE=docker backend-docker-image

echo "[INFO] Deployment finished. The application is running at http://localhost:8080"
