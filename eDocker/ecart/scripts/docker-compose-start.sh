#!/bin/zsh
cd "$(dirname "$0")"

export TAG="local-georgi"
export REGISTRY_HOST="docker.io"
export REGISTRY_REPO="ggrigorov1"

export OS_DATABASE_NAME="eOrderServiceDB"
export OS_DATABASE_USER="root"
export OS_DATABASE_PASS="toor"
export OS_DATABASE_URL="jdbc:postgresql://order-service-posgresql:5432/${OS_DATABASE_NAME}"

export PS_DATABASE_NAME="eProductServiceDB"
export PS_DATABASE_USER=${OS_DATABASE_USER}
export PS_DATABASE_PASS=${OS_DATABASE_PASS}
export PS_DATABASE_URL="jdbc:postgresql://product-service-posgresql:5432/${PS_DATABASE_NAME}"

export RRS_DATABASE_NAME="eRevRateServiceDB"
export RRS_DATABASE_USER=${OS_DATABASE_USER}
export RRS_DATABASE_PASS=${OS_DATABASE_PASS}
export RRS_DATABASE_URL="jdbc:postgresql://review-rating-service-posgresql:5432/${RRS_DATABASE_NAME}"

export

docker-compose build --pull
docker-compose up -d --force-recreate
