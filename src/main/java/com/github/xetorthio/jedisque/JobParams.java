package com.github.xetorthio.jedisque;

public class JobParams {

	private Integer replicate;
	private Integer delay;
	private Integer retry;
	private Integer ttl;
	private Integer maxlen;
	private Boolean async;
	
	public JobParams(Integer replicate, Integer delay, Integer retry, Integer ttl, Integer maxlen, Boolean async) {
		super();
		this.replicate = replicate;
		this.delay = delay;
		this.retry = retry;
		this.ttl = ttl;
		this.maxlen = maxlen;
		this.async = async;
	}

	public Integer getReplicate() {
		return replicate;
	}

	public void setReplicate(Integer replicate) {
		this.replicate = replicate;
	}

	public Integer getDelay() {
		return delay;
	}

	public void setDelay(Integer delay) {
		this.delay = delay;
	}

	public Integer getRetry() {
		return retry;
	}

	public void setRetry(Integer retry) {
		this.retry = retry;
	}

	public Integer getTTL() {
		return ttl;
	}

	public void setTTL(Integer ttl) {
		this.ttl = ttl;
	}

	public Integer getMaxlen() {
		return maxlen;
	}

	public void setMaxlen(Integer maxlen) {
		this.maxlen = maxlen;
	}

	public Boolean getAsync() {
		return async;
	}

	public void setAsync(Boolean async) {
		this.async = async;
	}
	
}



