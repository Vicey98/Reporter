FROM azul/zulu-openjdk:17
#ENV APP_HOME=/usr/app/
#WORKDIR $APP_HOME
COPY target/Reporter-1.0.0.jar reporter-1.0.0.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/reporter-1.0.0.jar"]