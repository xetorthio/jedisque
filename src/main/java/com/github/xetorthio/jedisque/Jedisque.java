package com.github.xetorthio.jedisque;

import java.util.List;
import java.util.Map;

import redis.clients.jedis.BuilderFactory;
import redis.clients.jedis.Protocol;
import redis.clients.util.SafeEncoder;

public class Jedisque extends redis.clients.jedis.Connection {
	static final private int DISQUE_PORT = 7711;

	public Jedisque() {
		super("localhost", DISQUE_PORT);
	}

	public String addJob(String queueName, String job, int mstimeout) {
		sendCommand(Commands.ADDJOB, SafeEncoder.encodeMany(queueName, job, String.valueOf(mstimeout)));
		return getBulkReply();
	}

	public List<Job> getJob(String... queueNames) {
		final byte[][] params = new byte[queueNames.length + 1][];
		params[0] = SafeEncoder.encode("FROM");
		System.arraycopy(SafeEncoder.encodeMany(queueNames), 0, params, 1, queueNames.length);
		sendCommand(Commands.GETJOB, params);
		return JedisqueBuilder.JOB_LIST.build(getObjectMultiBulkReply());
	}

	public Long ackjob(String... jobIds) {
		sendCommand(Commands.ACKJOB, jobIds);
		return getIntegerReply();
	}

	public String info() {
		sendCommand(Commands.INFO);
		return getBulkReply();
	}

	public String info(String section) {
		sendCommand(Commands.INFO, section);
		return getBulkReply();
	}

	public Long qlen(String queueName) {
		sendCommand(Commands.QLEN, queueName);
		return getIntegerReply();

	}

	public List<Job> qpeek(String queueName, int count) {
		sendCommand(Commands.QPEEK, SafeEncoder.encode(queueName), Protocol.toByteArray(count));
		return JedisqueBuilder.JOB_PEEK.build(getObjectMultiBulkReply());
	}

	public Long delJob(String jobId) {
		sendCommand(Commands.DELJOB, jobId);
		return getIntegerReply();
	}

	public Long dequeue(String ...jobIds) {
		sendCommand(Commands.DEQUEUE, jobIds);
		return getIntegerReply();
	}

	public Long enqueue(String ...jobIds) {
		sendCommand(Commands.ENQUEUE, jobIds);
		return getIntegerReply();
	}

}