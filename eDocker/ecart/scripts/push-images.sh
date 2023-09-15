#!/bin/zsh
cd "$(dirname "$0")"

cd ../../../product-service
mvn clean compile jib:build

cd ../noitificatrion-service
mvn clean compile jib:build

cd ../order-service
mvn clean compile jib:build

cd ../rev-rate-service
mvn clean compile jib:build