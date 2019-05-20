#!/usr/bin/env bash


chmod +x /app/scripts/wait-for-it.sh


/app/scripts/wait-for-it.sh mysqldb:3306 -t 180


/usr/bin/java -Djava.security.egd=file:/dev/./urandom -jar /opt/qms/quote-management-service.jar
