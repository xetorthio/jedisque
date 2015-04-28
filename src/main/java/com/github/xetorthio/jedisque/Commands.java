package com.github.xetorthio.jedisque;

import redis.clients.jedis.ProtocolCommand;
import redis.clients.util.SafeEncoder;

public enum Commands implements ProtocolCommand {
	ADDJOB, GETJOB, ACKJOB, INFO, QLEN, QPEEK, DELJOB, DEQUEUE, ENQUEUE;
	private final byte[] raw;

	Commands() {
		raw = SafeEncoder.encode(this.name());
	}

	@Override
	public byte[] getRaw() {
		return raw;
	}

}
