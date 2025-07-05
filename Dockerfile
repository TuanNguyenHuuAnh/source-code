# syntax=docker/dockerfile:experimental

ARG FROMIMAGE_BUILD=registry.dai-ichi-life.com.vn/base-images/maven:3.9.6-eclipse-temurin-11-alpine
ARG HOME_DIR=/app

ARG BE_PARENT_DIR=be-parent-feature
ARG JCANARY_PARENT_DIR=jcanary-parent
# admin-webapp or be-api
ARG BUILD_PROJECT=all

###################
# Build stage
###################

# Use the official maven/Java image to create a build artifact: https://hub.docker.com/_/maven
FROM ${FROMIMAGE_BUILD} as build
#from adoptopenjdk/maven-openjdk8 as build

ARG HOME_DIR
# Set the working directory inside the container
WORKDIR $HOME_DIR
# Set proxy settings
ARG MAVEN_OPTIONS
ARG MAVEN_OPTS="-Dhttps.protocols=TLSv1,TLSv1.1,TLSv1.2 -Daether.connector.https.securityMode=insecure $MAVEN_OPTIONS"
# -Dhttp.proxyHost=10.166.x.x -Dhttp.proxyPort=8080 -Dhttps.proxyHost=10.166.x.x -Dhttps.proxyPort=8080 -Dmaven.wagon.http.pool=false"
#RUN echo "MAVEN_OPTS: $MAVEN_OPTS"
#ENV JAVA_OPTS "$JAVA_OPTS -Djavax.net.ssl.trustStore=true"

#COPY settings.xml /root/.m2/settings.xml
#COPY resources/settings.xml $MAVEN_CONFIG/settings.xml
#COPY resources/settings.xml /usr/share/maven/ref/

COPY resources/libs ./libs
RUN --mount=type=cache,target=/root/.m2 mvn org.apache.maven.plugins:maven-install-plugin:3.1.1:install-file -Dfile=./libs/spring-data-mirage-0.11.1.RELEASE.jar -DgroupId=jp.xet.springframework.data.mirage \
    -DartifactId=spring-data-mirage -Dversion=0.11.1.RELEASE -Dpackaging=jar $MAVEN_OPTIONS \
    && mvn org.apache.maven.plugins:maven-install-plugin:3.1.1:install-file -Dfile=./libs/spar-wings-spring-data-chunk-0.35.jar -DgroupId=jp.xet.spar-wings \
    -DartifactId=spar-wings-spring-data-chunk -Dversion=0.35 -Dpackaging=jar $MAVEN_OPTIONS \
    && mvn org.apache.maven.plugins:maven-install-plugin:3.1.1:install-file -Dfile=./libs/jave-1.0.2.jar -DgroupId=it.sauronsoftware \
    -DartifactId=jave -Dversion=1.0.2 -Dpackaging=jar $MAVEN_OPTIONS

ARG BE_PARENT_DIR
ARG JCANARY_PARENT_DIR
# admin-webapp or be-api
ARG BUILD_PROJECT
# Copy source code
COPY $JCANARY_PARENT_DIR/ ./$JCANARY_PARENT_DIR/
COPY $BE_PARENT_DIR/ ./$BE_PARENT_DIR/

RUN date '+%d/%m/%Y %H:%M:%S' > ./$BE_PARENT_DIR/shared-resources/src/main/resources/templates/views/home/version-info.html \
    && date '+%d/%m/%Y %H:%M:%S' > ./$BE_PARENT_DIR/be-api/src/main/resources/version-info-api.txt

# Build a release artifact.
# RUN --mount=type=cache,target=/root/.m2 \
#     mvn -f $JCANARY_PARENT_DIR/pom.xml clean install -e -Dmaven.javadoc.skip=true -DskipTests \
#     && mvn -f $BE_PARENT_DIR/pom.xml clean install -e -Dmaven.javadoc.skip=true -DskipTests
RUN --mount=type=cache,target=/root/.m2 \
    mvn -f $JCANARY_PARENT_DIR/pom.xml clean install -e -Dmaven.javadoc.skip=true -DskipTests $MAVEN_OPTIONS \
    && if [ "$BUILD_PROJECT" = "all" ]; then \
        echo "Installing all..."; \
        mvn -f $BE_PARENT_DIR/pom.xml clean install -e -Dmaven.javadoc.skip=true -DskipTests $MAVEN_OPTIONS; \
    elif [ "$BUILD_PROJECT" = "admin-webapp" ]; then \
        echo "Installing admin-webapp"; \
        mvn -f $BE_PARENT_DIR/be-core/pom.xml clean install -e -Dmaven.javadoc.skip=true -DskipTests $MAVEN_OPTIONS \
        mvn -f $BE_PARENT_DIR/webapp-config/pom.xml clean install -e -Dmaven.javadoc.skip=true -DskipTests $MAVEN_OPTIONS; \
        mvn -f $BE_PARENT_DIR/shared-resources/pom.xml clean install -e -Dmaven.javadoc.skip=true -DskipTests $MAVEN_OPTIONS; \
        mvn -f $BE_PARENT_DIR/be-process/pom.xml clean install -e -Dmaven.javadoc.skip=true -DskipTests $MAVEN_OPTIONS; \
        mvn -f $BE_PARENT_DIR/cms-core/pom.xml clean install -e -Dmaven.javadoc.skip=true -DskipTests $MAVEN_OPTIONS; \
        mvn -f $BE_PARENT_DIR/cms-portal/pom.xml clean install -e -Dmaven.javadoc.skip=true -DskipTests $MAVEN_OPTIONS; \
        mvn -f $BE_PARENT_DIR/admin-webapp/pom.xml clean install -e -Dmaven.javadoc.skip=true -DskipTests $MAVEN_OPTIONS; \
    else \
        echo "Installing be-api"; \
        mvn -f $BE_PARENT_DIR/be-core/pom.xml clean install -e -Dmaven.javadoc.skip=true -DskipTests $MAVEN_OPTIONS \
        mvn -f $BE_PARENT_DIR/be-api-config/pom.xml clean install -e -Dmaven.javadoc.skip=true -DskipTests $MAVEN_OPTIONS; \
        mvn -f $BE_PARENT_DIR/cms-core/pom.xml clean install -e -Dmaven.javadoc.skip=true -DskipTests $MAVEN_OPTIONS; \
        mvn -f $BE_PARENT_DIR/be-api/pom.xml clean install -e -Dmaven.javadoc.skip=true -DskipTests $MAVEN_OPTIONS; \
    fi \
    && echo "Install done!"
