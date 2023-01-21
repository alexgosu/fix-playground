FROM adoptopenjdk/openjdk11:jre-11.0.16.1_1-alpine

EXPOSE 8080

ENV TZ=Asia/Almaty

RUN apk add tzdata &&  \
    cp /usr/share/zoneinfo/$TZ /etc/localtime &&  \
    echo $TZ > /etc/timezone

ADD ./build/libs/*.jar /opt/app-root/app.jar

ENTRYPOINT java -jar /opt/app-root/app.jar
