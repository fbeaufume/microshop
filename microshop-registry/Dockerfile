FROM openjdk:8-jre-alpine

EXPOSE 8761

COPY target/microshop-*.jar application.jar

ENV JAVA_OPTS="-Xms128m -Xmx256m"

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar application.jar"]
