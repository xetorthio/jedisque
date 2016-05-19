package com.github.xetorthio.jedisque;

import redis.clients.jedis.Connection;
import redis.clients.jedis.Protocol;
import redis.clients.jedis.ProtocolCommand;
import redis.clients.jedis.exceptions.JedisConnectionException;
import redis.clients.util.SafeEncoder;

import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

public class BinaryJedisque extends redis.clients.jedis.Connection {
    static final private int DISQUE_PORT = 7711;
    private final List<URI> uris = new ArrayList<URI>();
    private Random randomGenerator = new Random();
    private int timeout = 2000;

    public BinaryJedisque() {
        try {
            uris.add(new URI("disque://localhost:" + DISQUE_PORT));
        } catch (URISyntaxException e) {
        }
    }

    public BinaryJedisque(int timeout) {
        this();
        this.timeout = timeout;
    }

    public BinaryJedisque(URI... uris) {
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
                if (timeout > 0) {
                    super.connect();
                } else {

                    super.setConnectionTimeout(0);
                    super.connect();
                }
            } catch (JedisConnectionException e) {
                uris.remove(index);
            }
        }
    }

    public String addJob(byte[] queueName, byte[] job, long mstimeout) {
        sendCommand(Command.ADDJOB, queueName, job, Protocol.toByteArray(mstimeout));
        return getBulkReply();
    }

    public String addJob(byte[] queueName, byte[] job, long mstimeout, JobParams params) {
        List<byte[]> addJobCommand = new ArrayList<byte[]>();
        addJobCommand.add(queueName);
        addJobCommand.add(job);
        addJobCommand.add(Protocol.toByteArray(mstimeout));
        addJobCommand.addAll(params.getParams());
        sendCommand(Command.ADDJOB, addJobCommand.toArray(new byte[addJobCommand.size()][]));
        return getBulkReply();
    }


}