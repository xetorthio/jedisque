package com.github.xetorthio.jedisque;

import java.util.ArrayList;
import java.util.List;

public class JobInfo {

	private String id;
	private String queue;
	private String state;
	private long repl;
	private long ttl;
	private long ctime;
	private long delay;
	private long retry;
	private long nodesDelivered;
	private long nodesConfirmed;
	private List<String> requeueWithin;
	private List<String> awakeWithin;
	private String body;

	public JobInfo(String id, String queue, String state, long repl, long ttl, long ctime, long delay, long retry,
				   long nodesDelivered, long nodesConfirmed, List<String> requeueWithin, List<String> awakeWithin, String body) {
		super();
		this.id = id;
		this.queue = queue;
		this.state = state;
		this.repl = repl;
		this.ttl = ttl;
		this.ctime = ctime;
		this.delay = delay;
		this.retry = retry;
		this.nodesDelivered = nodesDelivered;
		this.nodesConfirmed = nodesConfirmed;
		this.requeueWithin = requeueWithin;
		this.awakeWithin = awakeWithin;
		this.body = body;
	}

	public String getId() {
		return id;
	}

	public String getQueue() {
		return queue;
	}

	public String getState() {
		return state;
	}

	public long getRepl() {
		return repl;
	}

	public long getTtl() {
		return ttl;
	}

	public long getCtime() {
		return ctime;
	}

	public long getDelay() {
		return delay;
	}

	public long getRetry() {
		return retry;
	}

	public long getNodesDelivered() {
		return nodesDelivered;
	}

	public long getNodesConfirmed() {
		return nodesConfirmed;
	}

	public List<String> getRequeueWithin() {
		return requeueWithin;
	}

	public List<String> getAwakeWithin() {
		return awakeWithin;
	}

	public String getBody() {
		return body;
	}
	
	
}
