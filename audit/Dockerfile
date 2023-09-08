FROM bellsoft/liberica-runtime-container:jdk-17-glibc
COPY /target/*.jar audit.jar
ENTRYPOINT java -jar audit.jar