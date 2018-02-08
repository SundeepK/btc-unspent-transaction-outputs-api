#!/bin/bash

BASE_DIR="$( dirname "$0" )"

export SPRING_PROFILES_ACTIVE=cucumber

TOMCAT_PORT=8080
if [ -n "$1" ]
then
  TOMCAT_PORT=$1
fi

activeProfiles=cucumber
cd $BASE_DIR
JARNAME="$(ls -rt ./build/libs/blockchain*.jar | tail -n 1)"
command="java
  -Duser.timezone=UTC \
  -jar $JARNAME \
  --spring.profiles.active=$activeProfiles
  --server.port=$TOMCAT_PORT"


echo "Running command: ${command}"
exec ${command}
