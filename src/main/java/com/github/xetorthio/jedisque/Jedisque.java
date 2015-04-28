package com.github.xetorthio.jedisque;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import redis.clients.jedis.Protocol;
import redis.clients.jedis.exceptions.JedisConnectionException;
import redis.clients.util.SafeEncoder;

public class Jedisque extends redis.clients.jedis.Connection {
	static final private int DISQUE_PORT = 7711;
	private final List<URI> uris = new ArrayList<URI>();
	private Random randomGenerator = new Random();

	public Jedisque() {
		try {
			uris.add(new URI("disque://localhost:" + DISQUE_PORT));
		} catch (URISyntaxException e) {
			// TODO:
		}
	}

	public Jedisque(URI... uris) {
		this.uris.addAll(Arrays.asList(uris));
	}

	@Override
	public void connect() {
		while (!this.isConnected()) {
			if (uris.size() == 0) {
				throw new JedisConnectionException("Could not connect to any of the provided nodes");
			}
			int index = randomGenerator.nextInt(uris.size());

			try {
				URI uri = uris.get(index);
				setHost(uri.getHost());
				setPort(uri.getPort());
				super.connect();
			} catch (JedisConnectionException e) {
				uris.remove(index);
			}
		}
	}

	public String addJob(String queueName, String job, int mstimeout) {
		sendCommand(Commands.ADDJOB, SafeEncoder.encodeMany(queueName, job, String.valueOf(mstimeout)));
		return getBulkReply();
	}

	public List<Job> getJob(String... queueNames) {
		final byte[][] params = new byte[queueNames.length + 1][];
		params[0] = Keyword.FROM.raw;
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

	public Long dequeue(String... jobIds) {
		sendCommand(Commands.DEQUEUE, jobIds);
		return getIntegerReply();
	}

	public Long enqueue(String... jobIds) {
		sendCommand(Commands.ENQUEUE, jobIds);
		return getIntegerReply();
	}

	public Long fastack(String ...jobIds) {
		sendCommand(Commands.FASTACK, jobIds);
		return getIntegerReply();
	}
	

	private enum Keyword {
		FROM;
		private final byte[] raw;

		Keyword() {
			raw = SafeEncoder.encode(this.name());
		}

	}
}