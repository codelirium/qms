FROM openjdk:11-jre

MAINTAINER codelirium


RUN mkdir -p /app/scripts

COPY ./docker/scripts/wait-for-it.sh /app/scripts

COPY ./docker/scripts/start.sh /app/scripts


RUN mkdir -p /opt/qms

COPY ./target/quote-management-service.jar /opt/qms


RUN mkdir -p /var/cache/qms


RUN chmod +x /app/scripts/start.sh

ENTRYPOINT ["/app/scripts/start.sh"]
