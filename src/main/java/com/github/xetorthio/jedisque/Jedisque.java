package com.github.xetorthio.jedisque;

import redis.clients.util.SafeEncoder;

public class Jedisque extends redis.clients.jedis.Connection {
	static final private int DISQUE_PORT = 7711;

	public Jedisque() {
		super("localhost", DISQUE_PORT);
	}

	public String addJob(String queueName, String job, int mstimeout) {
		sendCommand(
				Commands.ADDJOB,
				SafeEncoder.encodeMany(queueName, job,
						String.valueOf(mstimeout)));
		return getBulkReply();
	}
}