# Linux Cluster Monitoring Agent
## Introduction
The Linux Cluster Monitoring Agent is a tool that allows users to monitor nodes 
in a Linux cluster by tracking the node hardware as well as the usage information from the different nodes. 
This is done by provisioning and running a PostgreSQL instance within a Docker container and automated with the bash scripts
host_info.sh and host_usage.sh, creating tables then populating them. host_usage.sh will poll the connecting node every 60 seconds to check out resource usage. This is achieved by setting up a crontab job to run host_usage.sh every minute. When combined, this app will poll its users machines for data in order to see resource usage of provisioned virtual machine systems

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
### 3. host_usage.sh Usage
This script inserts a snapshot of the node's current resource usage into the host_usage table and can be ran manually
```
# Insert a snapshot of the node's resource usage into the host_usage table
./linux_sql/scripts/host_usage.sh psql_host psql_port db_name psql_user psql_password
```
4. crontab Setup
A crontab job can be created to repeatedly run the host_usage.sh script over a specified interval
```
# Run this to edit your current crontab jobs
crontab -e

# Enter the following line in the opened file to set up the job
* * * * * bash [path]/host_usage.sh psql_host psql_port db_name psql_user psql_password > /tmp/host_usage.log

# Verify that the job was successfully created by listing crontab jobs
crontab -ls

# Verify that the script is running as intended by checking the log file
cat /tmp/host_usage.log
