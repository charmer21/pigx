FROM anapsix/alpine-java:8_server-jre_unlimited

MAINTAINER wangiegie@gmail.com

RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime

RUN mkdir -p /pigx-activiti

WORKDIR /pigx-activiti

EXPOSE 5005

ADD ./pigx-visual/pigx-activiti/target/pigx-activiti.jar ./

CMD sleep 30;java -Xms128m -Xmx256m -Djava.security.egd=file:/dev/./urandom -jar pigx-activiti.jar
