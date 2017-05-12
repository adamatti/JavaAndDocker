FROM java:8-jdk

RUN mkdir /app
WORKDIR /app

ADD gradle /app/gradle
ADD gradlew /app
RUN ./gradlew tasks

ADD build.gradle /app
RUN ./gradlew idea

ADD . /app
RUN ./gradlew classes
