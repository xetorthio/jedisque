package com.github.xetorthio.jedisque;

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
	private List<String> nodesDelivered;
	private List<String> nodesConfirmed;
	private long requeueWithin;
	private long awakeWithin;
	private String body;

	public JobInfo(String id, String queue, String state, long repl, long ttl, long ctime, long delay, long retry,
			List<String> nodesDelivered, List<String> nodesConfirmed, long requeueWithin, long awakeWithin, String body) {
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

	public List<String> getNodesDelivered() {
		return nodesDelivered;
	}

	public List<String> getNodesConfirmed() {
		return nodesConfirmed;
	}

	public long getRequeueWithin() {
		return requeueWithin;
	}

	public long getAwakeWithin() {
		return awakeWithin;
	}

	public String getBody() {
		return body;
	}
	
	
}
