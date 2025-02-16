FROM ubuntu:latest AS build

# Atualiza o repositório e instala o OpenJDK e outras dependências necessárias
RUN apt-get update && apt-get install -y openjdk-17-jdk curl wget gnupg2


RUN apt-get update && apt-get install -y maven


# Configura o Maven
ENV MAVEN_HOME /opt/apache-maven-3.8.4
ENV PATH $MAVEN_HOME/bin:$PATH

COPY . .

# Limpeza e construção do projeto
RUN mvn clean install -DskipTests

FROM openjdk:17-jdk-slim

EXPOSE 8090

COPY --from=build /target/SonoInteligente-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT [ "java", "-jar", "app.jar"]
