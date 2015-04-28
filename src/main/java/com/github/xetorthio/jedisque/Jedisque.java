package com.github.xetorthio.jedisque;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

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
				throw new JedisConnectionException(
						"Could not connect to any of the provided nodes");
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
		sendCommand(
				Commands.ADDJOB,
				SafeEncoder.encodeMany(queueName, job,
						String.valueOf(mstimeout)));
		return getBulkReply();
	}

	public String info() {
		sendCommand(Commands.INFO);
		return getBulkReply();
	}
}