FROM anapsix/alpine-java:8_server-jre_unlimited

MAINTAINER wangiegie@gmail.com

RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime

RUN mkdir -p /pigx-daemon-quartz

WORKDIR /pigx-daemon-quartz

EXPOSE 5007

ADD ./pigx-visual/pigx-daemon-quartz/target/pigx-daemon-quartz.jar ./

CMD sleep 30;java -Xms128m -Xmx256m -Djava.security.egd=file:/dev/./urandom -jar pigx-daemon-quartz.jar
