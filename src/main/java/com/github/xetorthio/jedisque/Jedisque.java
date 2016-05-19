package com.github.xetorthio.jedisque;

import redis.clients.jedis.Protocol;
import redis.clients.util.SafeEncoder;

import java.net.URI;
import java.util.List;
import java.util.Random;

public class Jedisque extends BinaryJedisque {
	private Random randomGenerator = new Random();

	public Jedisque() {
		super();
	}
	public  Jedisque(int timeout){
		super(timeout);
	}

	public Jedisque(URI... uris) {
		super(uris);
	}

	public String addJob(String queueName, String job, long mstimeout) {
		return addJob(SafeEncoder.encode(queueName), SafeEncoder.encode(job), mstimeout);
	}

	public String addJob(String queueName, String job, long mstimeout, JobParams params) {
		return addJob(SafeEncoder.encode(queueName), SafeEncoder.encode(job), mstimeout, params);
	}

	public List<Job> getJob(String... queueNames) {
		final byte[][] params = new byte[queueNames.length + 1][];
		params[0] = Keyword.FROM.raw;
		System.arraycopy(SafeEncoder.encodeMany(queueNames), 0, params, 1, queueNames.length);
		sendCommand(Command.GETJOB, params);
		return JedisqueBuilder.JOB_LIST.build(getObjectMultiBulkReply());
	}


	public List<Job> getJob(long timeout, long count, String ...queueNames) {
		final byte[][] params = new byte[queueNames.length + 5][];
		params[0] = Keyword.TIMEOUT.raw;
		params[1] = Protocol.toByteArray(timeout);
		params[2] = Keyword.COUNT.raw;
		params[3] = Protocol.toByteArray(count);
		params[4] = Keyword.FROM.raw;
		System.arraycopy(SafeEncoder.encodeMany(queueNames), 0, params, 5, queueNames.length);
		sendCommand(Command.GETJOB, params);
		return JedisqueBuilder.JOB_LIST.build(getObjectMultiBulkReply());
	}

	public Long ackjob(String... jobIds) {
		sendCommand(Command.ACKJOB, jobIds);
		return getIntegerReply();
	}

	public String info() {
		sendCommand(Command.INFO);
		return getBulkReply();
	}

	public String info(String section) {
		sendCommand(Command.INFO, section);
		return getBulkReply();
	}

	public Long qlen(String queueName) {
		sendCommand(Command.QLEN, queueName);
		return getIntegerReply();

	}

	public List<Job> qpeek(String queueName, long count) {
		sendCommand(Command.QPEEK, SafeEncoder.encode(queueName), Protocol.toByteArray(count));
		return JedisqueBuilder.JOB_LIST.build(getObjectMultiBulkReply());
	}

	public Long delJob(String jobId) {
		sendCommand(Command.DELJOB, jobId);
		return getIntegerReply();
	}

	public Long dequeue(String... jobIds) {
		sendCommand(Command.DEQUEUE, jobIds);
		return getIntegerReply();
	}

	public Long enqueue(String... jobIds) {
		sendCommand(Command.ENQUEUE, jobIds);
		return getIntegerReply();
	}

	public Long fastack(String ...jobIds) {
		sendCommand(Command.FASTACK, jobIds);
		return getIntegerReply();
	}
	

	public JobInfo show(String jobId) {
		sendCommand(Command.SHOW, jobId);
		return JedisqueBuilder.JOB_SHOW.build(getObjectMultiBulkReply());
	}
	
	
	public String ping() {
		sendCommand(Command.PING);
		return getBulkReply();
	}

	public Long working(String jobId) {
		sendCommand(Command.WORKING, jobId);
		return getIntegerReply();
	}
	
}