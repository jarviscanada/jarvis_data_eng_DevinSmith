!# /bin/bash

api_key=$1
psql_host=$2
psql_port=$3
psql_database=$4
psql_username=$5
psql_password=$6
# I would need to have something to allow multiple inputs but dont so im using a dummy statement
data_out=$(curl --request GET \
	--url 'https://alpha-vantage.p.rapidapi.com/query?function=GLOBAL_QUOTE&symbol=MSFT&datatype=json' \
	--header 'X-RapidAPI-Host: alpha-vantage.p.rapidapi.com' \
	--header 'X-RapidAPI-Key: REMOVEDFORSECURITYREASONS' | jq)

if [ "$#" -lt 7 ]; then
     echo "Illegal number of parameters"
     exit 1
fi
# needs to loop through the different variables for each one but this is all dummy
symbol_out=$(echo "$data_out" | egrep "symbol" | awk '{print substr($3, 1, length($3)-1)}' | xargs)
open_out=1
high_out=1
low_out=1
price_out=1
volume_out=1


# this logic would follow through for the rest of the data 
insert_stmt="INSERT INTO quotes(id, symbol, open, high, low, price, volume)
             VALUES(DEFAULT, '$symbol_out', '$open_out', '$high_out', '$low_out',
             '$price_out', '$volume_out' )";

export PGPASSWORD=$psql_password

psql -h $psql_host -p $psql_port -d $db_name -U $psql_user -c "insert_stmt"

exit $?
