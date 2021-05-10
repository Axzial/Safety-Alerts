FROM adoptopenjdk:11-jre-hotspot
VOLUME /tmp
ADD /target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]
