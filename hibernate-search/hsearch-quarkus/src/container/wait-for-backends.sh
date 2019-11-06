#!/bin/bash
# wait-for-backends.sh

set -e

for ES_HOST in ${ES_HOSTS//,/ }
do
    ES_HOST_NO_PROTOCOL=${ES_HOST#http://}
    ES_HOST_NO_PROTOCOL=${ES_HOST_NO_PROTOCOL#https://}
    ES_HOST_NO_PORT=$(cut -d ':' -f1 <<<"$ES_HOST_NO_PROTOCOL")
    ES_PORT=$(cut -d ':' -f2 <<<"$ES_HOST_NO_PROTOCOL")
    until $(>&2 2>/dev/null nc -z "${ES_HOST_NO_PORT}" "${ES_PORT}")
    do
	>&2 echo "$ES_HOST is not available, waiting..."
        sleep 2
    done
done

until $(>&2 2>/dev/null nc -z "$POSTGRES_HOST" 5432)
do
    >&2 echo "$POSTGRES_HOST:5432 is not available, waiting..."
    sleep 2
done
