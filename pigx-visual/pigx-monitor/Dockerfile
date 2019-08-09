FROM anapsix/alpine-java:8_server-jre_unlimited

MAINTAINER wangiegie@gmail.com

RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime

RUN mkdir -p /pigx-monitor

WORKDIR /pigx-monitor

EXPOSE 5001

ADD ./pigx-visual/pigx-monitor/target/pigx-monitor.jar ./

CMD sleep 30;java -Xms128m -Xmx256m -Djava.security.egd=file:/dev/./urandom -jar pigx-monitor.jar
