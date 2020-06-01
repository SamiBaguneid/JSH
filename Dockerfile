FROM maven:3.6-jdk-11

ENV DEBIAN_FRONTEND=noninteractive

RUN apt-get update \
    && apt-get -y install --no-install-recommends python3 2>&1 \
    && apt-get -y install --no-install-recommends apt-utils dialog 2>&1 \
    && apt-get -y install git procps lsb-release \
    && apt-get autoremove -y \
    && apt-get clean -y \
    && rm -rf /var/lib/apt/lists/*

COPY src /jsh/src
COPY pom.xml /jsh/pom.xml
COPY jsh /jsh/jsh
RUN chmod +x /jsh/jsh
COPY analysis /jsh/analysis
COPY test /jsh/test
COPY coverage /jsh/coverage
COPY testFiles jsh/testFiles

RUN cd /jsh && mvn package

ENV DEBIAN_FRONTEND=

EXPOSE 8000
