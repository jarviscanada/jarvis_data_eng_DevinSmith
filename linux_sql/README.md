# Linux Cluster Monitoring Agent
## Introduction
The Linux Cluster Monitoring Agent is a tool that allows users to monitor nodes 
in a linux cluster by tracking the node hardware as well as the usage information from the different nodes. 
This is done by provisioning and running a PostgreSQL instance within a Docker container and automated with the bash scripts
host_info.sh and host_usage.sh, creating tables then populating them. host_usage.sh will poll the connecting node every 60 seconds
to check out resource usage. This is achieved by setting up a crontab job to run host_usage.sh every minute. When combined, this app will poll its users machines for data in order to see resource usage of provisioned virtual machine systems

## Quickstart
### 1. Database and Table Initialization
Before running the bash agent, the PosgreSQL instance is allocated by creating and starting up a docker container, creating the psql instance and then creating the host_info and host_usage tables.
```
# From the repository's home directory, allocate and create a psql instance with docker
./linux_sql/scripts/psql_docker.sh start db_password

# Initialize the database and tables
psql -h psql_host -U psql_user -W -f linux_sql/sql/ddl.sql
```
### 2. host_info.sh Usage
This script only needs to be run once per node to insert the node's hardware specifications into the host_info table
```
# Insert node hardware specifications into the host_info table
./linux_sql/scripts/host_info.sh psql_host psql_port db_name psql_user psql_password
```
