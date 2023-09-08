FROM bellsoft/liberica-runtime-container:jdk-17-glibc
COPY /target/*.jar gateway.jar
ENTRYPOINT java -jar gateway.jar