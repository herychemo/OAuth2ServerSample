

docker cp ./schema-data.sql PostgresOauth2ServerSample:/schema-data.sql


docker exec -it PostgresOauth2ServerSample psql -U postgres -d postgres -f schema-data.sql


