PATH := ./disque-git/src:${PATH}

define DISQUE_CONF
daemonize yes
port 7711
pidfile /tmp/disque1.pid
logfile /tmp/disque1.log
appendonly no
endef

export DISQUE_CONF
start: cleanup
	echo "$$DISQUE_CONF" | disque-server -

cleanup:

stop:
	kill `cat /tmp/disque1.pid`

test:
	make start
	sleep 2
	mvn -Dtest=${TEST} clean compile test
	make stop

release:
	make start
	mvn clean release:clean release:prepare
	mvn release:perform
	make stop

format:
	mvn java-formatter:format

travis-install:
	[ ! -e redis-git ] && git clone https://github.com/antirez/disque.git disque-git || true
	make -C redis-git -j4

.PHONY: test
