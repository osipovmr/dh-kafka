#!/bin/bash

set -e

producer_image_name="osipovmr/dh-kafka-producer:latest"
producer_container_name="kafka-producer"
consumer_image_name="osipovmr/dh-kafka-consumer:latest"
consumer_container_name="kafka-consumer"
docker_file_path="."

# Stop and remove any containers with the specified name
if docker ps -q -f "name=${producer_container_name}" | grep -q .; then
  echo "Stopping and removing container: $producer_container_name"
  docker stop "$producer_container_name"
  docker rm "$producer_container_name"
else
  echo "No container with name '$producer_container_name' found."
fi
if docker ps -q -f "name=${consumer_container_name}" | grep -q .; then
  echo "Stopping and removing container: $consumer_container_name"
  docker stop "$consumer_container_name"
  docker rm "$consumer_container_name"
else
  echo "No container with name '$consumer_container_name' found."
fi

# Remove the old image
echo "Removing image: $producer_image_name"
docker rmi "$producer_image_name" || true
echo "Removing image: $consumer_container_name"
docker rmi "$consumer_container_name" || true

# Build the new image
cd producer
mvn package
echo "Building image: $producer_image_name"
docker image build --platform linux/amd64 -t "$producer_image_name" "$docker_file_path"
cd ..
cd consumer
mvn package
echo "Building image: $consumer_image_name"
docker image build --platform linux/amd64 -t "$consumer_image_name" "$docker_file_path"
echo "Image built and tagged successfully."
cd ..
#docker compose -f docker-compose.yml -p dh-kafka up -d
docker compose -f docker-compose_3.yml -p dh-kafka up -d