FROM anapsix/alpine-java:8_server-jre_unlimited

MAINTAINER wangiegie@gmail.com

RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime

RUN mkdir -p /pigx-demo

WORKDIR /pigx-demo

EXPOSE 5003

ADD ./pigx-visual/pigx-demo/target/pigx-demo.jar ./

CMD sleep 30;java -Djava.security.egd=file:/dev/./urandom -jar pigx-demo.jar