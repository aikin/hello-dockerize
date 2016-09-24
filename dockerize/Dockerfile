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

WORKDIR /hello-dockerize

EXPOSE 8080
EXPOSE 5005
CMD ["bash"]