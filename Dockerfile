FROM adoptopenjdk/openjdk11:alpine-slim
ENV SPRING_PROFILES_ACTIVE=dev

# Needed to fix 'Fontconfig warning: ignoring C.UTF-8: not a valid language tag'
ENV LANG en_GB.UTF-8

# JRE fails to load fonts if there are no standard fonts in the image; DejaVu is a good choice,
# see https://github.com/docker-library/openjdk/issues/73#issuecomment-207816707

RUN apk add --update ttf-dejavu && rm -rf /var/cache/apk/*

EXPOSE 8080

COPY target/*.jar /opt/
CMD $JAVA_HOME/bin/java $JAVA_OPTS -jar /opt/*.jar
