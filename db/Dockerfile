FROM mysql:8.0.16

MAINTAINER lengleng(wangiegie@gmail.com)

RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime

COPY ./db/1schema.sql /docker-entrypoint-initdb.d

COPY ./db/2pigxx.sql /docker-entrypoint-initdb.d

COPY ./db/3pigxx_ac.sql /docker-entrypoint-initdb.d

COPY ./db/4pigxx_job.sql /docker-entrypoint-initdb.d

COPY ./db/5pigxx_mp.sql /docker-entrypoint-initdb.d

COPY ./db/6pigxx_config.sql /docker-entrypoint-initdb.d

COPY ./db/7pigxx_pay.sql /docker-entrypoint-initdb.d