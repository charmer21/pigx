FROM anapsix/alpine-java:8_server-jre_unlimited

MAINTAINER wangiegie@gmail.com

RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime

RUN mkdir -p /pigx-mp-manager

WORKDIR /pigx-mp-manager

EXPOSE 6000

ADD ./pigx-visual/pigx-mp-manager/target/pigx-mp-manager.jar ./

CMD sleep 30;java -Xms128m -Xmx256m -Djava.security.egd=file:/dev/./urandom -jar pigx-mp-manager.jar
