FROM amazoncorretto:21-alpine3.19
COPY target/stockquote-0.0.1-jar-with-dependencies.jar /usr/local/app/stockquote/stockquote.jar

ENTRYPOINT [ "java","-jar","/usr/local/app/stockquote/stockquote.jar"]

