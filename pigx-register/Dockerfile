FROM anapsix/alpine-java:8_server-jre_unlimited

MAINTAINER wangiegie@gmail.com

RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime

RUN mkdir -p /pigx-register

WORKDIR /pigx-register

EXPOSE 8848

ADD ./pigx-register/target/pigx-register.jar ./


CMD sleep 20;java -Xms128m -Xmx256m -Djava.security.egd=file:/dev/./urandom -jar pigx-register.jar


