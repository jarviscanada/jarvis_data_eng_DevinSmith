#!/bin/bash

psql_host=$1
psql_port=$2
db_name=$3
psql_user=$4
psql_password=$5

if [ "$#" -ne 5 ]; then
    echo "Illegal number of parameters"
    exit 1
fi

lscpu_out=$(lscpu)
hostname=$(hostname -f)
cpu_number=$(echo "$lscpu_out"  | egrep "^CPU\(s\):" | awk '{print $2}' | xargs)
echo "$cpu_number"
cpu_architecture=$(echo "$lscpu_out" | egrep "^Architecture:" | awk '{print $2}' | xargs)
echo "$cpu_architecture"
cpu_model=$(echo "$lscpu_out" | egrep "^Model\sname:" | awk '{print $3, $4, $5, $6, $7}' | xargs)
echo "$cpu_model"
cpu_mhz=$(echo "$lscpu_out" | egrep "^CPU\sMHz:" | awk '{print $3}' | xargs)
echo "$cpu_mhz"
l2_cache=$(echo "$lscpu_out" | egrep "L2\scache:" | awk '{print $3}' | xargs | grep -oE '[0-9]+')
echo "$l2_cache"
time_stamp=$(vmstat -t | awk '{print $18, $19}' | tail -n1 | xargs)
echo "$time_stamp"
total_mem=$(grep MemTotal /proc/meminfo | awk '{print $2, $3}' | xargs | grep -oE '[0-9]+')
echo "$total_mem"


insert_stmt="INSERT INTO host_info (id, hostname, cpu_number, cpu_architecture, cpu_model, cpu_mhz, l2_cache, "timestamp", total_mem) 
             VALUES(DEFAULT, '$hostname', '$cpu_number', '$cpu_architecture', '$cpu_model', '$cpu_mhz', '$l2_cache', '$time_stamp', '$total_mem')";

export PGPASSWORD=$psql_password 

psql -h $psql_host -p $psql_port -d $db_name -U $psql_user -c "$insert_stmt"
exit $?
