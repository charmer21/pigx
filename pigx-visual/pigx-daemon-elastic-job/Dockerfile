FROM anapsix/alpine-java:8_server-jre_unlimited

MAINTAINER wangiegie@gmail.com

RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime

RUN mkdir -p /pigx-daemon-elastic-job

WORKDIR /pigx-daemon-elastic-job

EXPOSE 5002

ADD ./pigx-visual/pigx-daemon-elastic-job/target/pigx-daemon-elastic-job.jar ./

CMD sleep 30;java -Xms128m -Xmx256m -Djava.security.egd=file:/dev/./urandom -jar pigx-daemon-elastic-job.jar
