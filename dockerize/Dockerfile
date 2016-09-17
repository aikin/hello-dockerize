FROM ubuntu:14.04

RUN apt-get update


ENV VERSION 8
ENV UPDATE 66
ENV BUILD 17

ENV JAVA_HOME /usr/lib/jvm/java-${VERSION}-oracle
ENV JRE_HOME ${JAVA_HOME}/jre


RUN apt-get install ca-certificates curl \
        gcc libc6-dev libssl-dev make \
        -y --no-install-recommends

RUN	curl --silent --location --retry 3 --cacert /etc/ssl/certs/GeoTrust_Global_CA.pem \
	--header "Cookie: oraclelicense=accept-securebackup-cookie;" \
	http://download.oracle.com/otn-pub/java/jdk/"${VERSION}"u"${UPDATE}"-b"${BUILD}"/server-jre-"${VERSION}"u"${UPDATE}"-linux-x64.tar.gz \
	| tar xz -C /tmp

RUN	mkdir -p /usr/lib/jvm && mv /tmp/jdk1.${VERSION}.0_${UPDATE} "${JAVA_HOME}"

RUN apt-get install -y openssl

RUN apt-get remove --purge --auto-remove -y \
        gcc \
        libc6-dev \
        libssl-dev \
        make

RUN	apt-get autoclean && apt-get --purge -y autoremove

RUN	rm -rf /var/lib/apt/lists/* /tmp/* /var/tmp/*

RUN update-alternatives --install "/usr/bin/java" "java" "${JRE_HOME}/bin/java" 1 && \
	update-alternatives --install "/usr/bin/javac" "javac" "${JAVA_HOME}/bin/javac" 1 && \
	update-alternatives --set java "${JRE_HOME}/bin/java" && \
	update-alternatives --set javac "${JAVA_HOME}/bin/javac"

# === Install Mysql ===

RUN groupadd -r mysql && useradd -r -g mysql mysql

RUN mkdir /docker-entrypoint-initdb.d

ENV MYSQL_MAJOR 5.7
ENV MYSQL_VERSION 5.7.11-1ubuntu14.04

# gpg: key 5072E1F5: public key "MySQL Release Engineering <mysql-build@oss.oracle.com>" imported
RUN apt-key adv --keyserver ha.pool.sks-keyservers.net --recv-keys A4A9406876FCBD3C456770C88C718D3B5072E1F5

RUN echo "deb http://repo.mysql.com/apt/ubuntu/ trusty mysql-${MYSQL_MAJOR}" > /etc/apt/sources.list.d/mysql.list

# the "/var/lib/mysql" stuff here is because the mysql-server postinst doesn't have an explicit way to disable the mysql_install_db codepath besides having a database already "configured" (ie, stuff in /var/lib/mysql/mysql)
# also, we set debconf keys to make APT a little quieter
RUN { \
		echo mysql-community-server mysql-community-server/data-dir select ''; \
		echo mysql-community-server mysql-community-server/root-pass password ''; \
		echo mysql-community-server mysql-community-server/re-root-pass password ''; \
		echo mysql-community-server mysql-community-server/remove-test-db select false; \
	} | debconf-set-selections

RUN apt-get update
RUN apt-get install -y mysql-server="${MYSQL_VERSION}"
RUN rm -rf /var/lib/apt/lists/*
RUN rm -rf /var/lib/mysql && mkdir -p /var/lib/mysql

# comment out a few problematic configuration values
# don't reverse lookup hostnames, they are usually another container
RUN sed -Ei 's/^(bind-address|log)/#&/' /etc/mysql/my.cnf \
	&& echo 'skip-host-cache\nskip-name-resolve' | awk '{ print } $1 == "[mysqld]" && c == 0 { c = 1; system("cat") }' /etc/mysql/my.cnf > /tmp/my.cnf \
	&& mv /tmp/my.cnf /etc/mysql/my.cnf

VOLUME /var/lib/mysql

COPY docker-entrypoint.sh /usr/local/bin/
RUN chmod +x /usr/local/bin/docker-entrypoint.sh
RUN ln -s usr/local/bin/docker-entrypoint.sh /entrypoint.sh # backwards compat
ENTRYPOINT ["docker-entrypoint.sh"]

WORKDIR /hello-dockerize

EXPOSE 3306
EXPOSE 8080
EXPOSE 5005
CMD ["mysqld"]