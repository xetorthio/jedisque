package com.github.xetorthio.jedisque;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
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
		sendCommand(Command.ADDJOB, SafeEncoder.encodeMany(queueName, job, String.valueOf(mstimeout)));
		return getBulkReply();
	}

	public String addJob(String queueName, String job, int mstimeout, JobParams params) {
		List<String> addJobCommand = new LinkedList<String>();
		addJobCommand.add(queueName);
		addJobCommand.add(job);
		addJobCommand.add(String.valueOf(mstimeout));
		addJobCommand.addAll(getJobParamsAsList(params));
		sendCommand(Command.ADDJOB, addJobCommand.toArray(new String[addJobCommand.size()]));
		return getBulkReply();
	}

	public List<Job> getJob(String... queueNames) {
		final byte[][] params = new byte[queueNames.length + 1][];
		params[0] = Keyword.FROM.raw;
		System.arraycopy(SafeEncoder.encodeMany(queueNames), 0, params, 1, queueNames.length);
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

	public List<Job> qpeek(String queueName, int count) {
		sendCommand(Command.QPEEK, SafeEncoder.encode(queueName), Protocol.toByteArray(count));
		return JedisqueBuilder.JOB_PEEK.build(getObjectMultiBulkReply());
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
	
	
	private List<String> getJobParamsAsList(JobParams params) {
		List<String> jobParams = new LinkedList<String>();
		if (params.getReplicate() != null) {
			jobParams.add(SafeEncoder.encode(Keyword.REPLICATE.raw));
			jobParams.add(String.valueOf(params.getReplicate()));
		} else if (params.getDelay() != null) {
			jobParams.add(SafeEncoder.encode(Keyword.DELAY.raw));
			jobParams.add(String.valueOf(params.getDelay()));
		} else if (params.getRetry() != null) {
			jobParams.add(SafeEncoder.encode(Keyword.RETRY.raw));
			jobParams.add(String.valueOf(params.getRetry()));
		} else if (params.getTTL() != null) {
			jobParams.add(SafeEncoder.encode(Keyword.TTL.raw));
			jobParams.add(String.valueOf(params.getTTL()));
		} else if (params.getMaxlen() != null) {
			jobParams.add(SafeEncoder.encode(Keyword.MAXLEN.raw));
			jobParams.add(String.valueOf(params.getMaxlen()));
		} else if (params.getAsync() != null && params.getAsync().booleanValue()) {
			jobParams.add(String.valueOf(Keyword.ASYNC.raw));
		}
		
		
		return jobParams;
	}



	
}