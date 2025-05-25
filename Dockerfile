FROM ubuntu:20.04 AS builder

ENV DEBIAN_FRONTEND=noninteractive
ENV JAVA_HOME=/opt/jdk
ENV PATH="${JAVA_HOME}/bin:${PATH}"

RUN apt-get update && apt-get install -y \
    curl \
    unzip \
    maven \
    git \
    && rm -rf /var/lib/apt/lists/*


RUN curl -L -o /tmp/jdk18.tar.gz https://github.com/adoptium/temurin18-binaries/releases/download/jdk-18.0.2.1+1/OpenJDK18U-jdk_x64_linux_hotspot_18.0.2.1_1.tar.gz && \
    mkdir -p /opt && \
    tar -xzf /tmp/jdk18.tar.gz -C /opt && \
    mv /opt/jdk-18.0.2.1+1 /opt/jdk && \
    rm /tmp/jdk18.tar.gz

WORKDIR /app
COPY . .

RUN mvn clean package -DskipTests

FROM ubuntu:20.04

ENV DEBIAN_FRONTEND=noninteractive
ENV JAVA_HOME=/opt/jdk
ENV PATH="${JAVA_HOME}/bin:${PATH}"

RUN apt-get update && apt-get install -y curl unzip && rm -rf /var/lib/apt/lists/*

RUN curl -L -o /tmp/jdk18.tar.gz https://github.com/adoptium/temurin18-binaries/releases/download/jdk-18.0.2.1+1/OpenJDK18U-jdk_x64_linux_hotspot_18.0.2.1_1.tar.gz && \
    mkdir -p /opt && \
    tar -xzf /tmp/jdk18.tar.gz -C /opt && \
    mv /opt/jdk-18.0.2.1+1 /opt/jdk && \
    rm /tmp/jdk18.tar.gz

WORKDIR /app

COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
