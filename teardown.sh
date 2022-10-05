#!/usr/bin/env bash

DATABASE=scalardb
USERNAME=scalaruser

psql postgres -c "drop database $DATABASE"
psql postgres -c "drop user $USERNAME"

