package com.github.xetorthio.jedisque;

public class JobParams {

	private Integer replicate;
	private Integer delay;
	private Integer retry;
	private Integer ttl;
	private Integer maxlen;
	private Boolean async;

	public Integer getReplicate() {
		return replicate;
	}

	public JobParams setReplicate(Integer replicate) {
		this.replicate = replicate;
		return this;
	}

	public Integer getDelay() {
		return delay;
	}

	public JobParams setDelay(Integer delay) {
		this.delay = delay;
		return this;
	}

	public Integer getRetry() {
		return retry;
	}

	public JobParams setRetry(Integer retry) {
		this.retry = retry;
		return this;
	}

	public Integer getTTL() {
		return ttl;
	}

	public JobParams setTTL(Integer ttl) {
		this.ttl = ttl;
		return this;
	}

	public Integer getMaxlen() {
		return maxlen;
	}

	public JobParams setMaxlen(Integer maxlen) {
		this.maxlen = maxlen;
		return this;
	}

	public Boolean getAsync() {
		return async;
	}

	public JobParams setAsync(Boolean async) {
		this.async = async;
		return this;
	}
	
}



