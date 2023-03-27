FROM maven:3-openjdk-17 as builder
WORKDIR /alist
COPY ../pom.xml /alist
COPY /src /alist/src
RUN mvn package -DskipTests=true

FROM openjdk:17
ENV JAVA_TOOL_OPTIONS="-Xss256K -XX:ReservedCodeCacheSize=64M -XX:MaxMetaspaceSize=100000K -Xmx64M"
WORKDIR /usr/alistapp
COPY --from=builder /alist/target/*.jar /usr/alistapp
EXPOSE 8443
ENTRYPOINT ["java", "-jar", "alist.jar"]
