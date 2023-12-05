# Linux Cluster Monitoring Agent
## Introduction
The Linux Cluster Monitoring Agent is a tool that allows users to monitor nodes 
in a linux cluster by tracking the node hardware as well as the usage information from the different nodes. 
This is done by provisioning and running a PostgreSQL instance within a Docker container and automated with the bash scripts
host_info.sh and host_usage.sh, creating tables then populating them. host_usage.sh will poll the connecting node every 60 seconds
to check out resource usage. This is achieved by setting up a crontab job to run host_usage.sh every minute. When combined, this app will poll its 
users machines for data in order to see resource usage of provisioned virtual machine systems

## Quickstart
