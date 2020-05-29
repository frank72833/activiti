FROM adoptopenjdk/openjdk11:alpine-slim
ENV SPRING_PROFILES_ACTIVE=dev

EXPOSE 8080

COPY target/*.jar /opt/
CMD $JAVA_HOME/bin/java $JAVA_OPTS -jar /opt/*.jar
