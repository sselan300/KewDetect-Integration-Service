# Start with a base image containing Java runtime https://hub.docker.com/r/adoptopenjdk/openjdk11
FROM gradle:6.8.3-jdk11 as builder
#Possibility to set JVM options (https://www.oracle.com/technetwork/java/javase/tech/vmoptions-jsp-140102.html)
#Set app home folder
ENV APP_HOME=/home/root/build/
WORKDIR $APP_HOME
COPY . .
RUN gradle clean build

# Start with a base image containing Java runtime
FROM openjdk:11-slim as runtime
ENV JAVA_OPTS="-Dspring.profiles.active=alibaba"
WORKDIR /home/root/nfis/
COPY --from=builder /home/root/build/build/libs/nfis_intergration_service-0.1.0.jar $WORKDIR
EXPOSE 8088
ENTRYPOINT [ "sh", "-c", "java -jar $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom nfis_intergration_service-0.1.0.jar" ]
