#!/usr/bin/env bash

DATABASE=scalardb
USERNAME=scalaruser
PASSWORD=scalarpassword

if [[ -z $SCHEMA_LOADER_JAR_PATH ]]; then
  echo 'SCHEMA_LOADER_JAR_PATH must be set'
  exit 1
fi

psql postgres -c "create database $DATABASE"
psql postgres -c "create user $USERNAME password '$PASSWORD'"

set -euxo pipefail
psql postgres -c "grant CREATE on database $DATABASE to $USERNAME"
java -jar $SCHEMA_LOADER_JAR_PATH --coordinator --config scalardb.properties -f player-battle.json

