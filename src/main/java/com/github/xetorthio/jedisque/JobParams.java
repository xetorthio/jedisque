package com.github.xetorthio.jedisque;

import redis.clients.jedis.Protocol;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class JobParams {

	private List<byte[]> params = new ArrayList<byte[]>();

	public JobParams setReplicate(Integer replicate) {
		params.add(Keyword.REPLICATE.raw);
		params.add(Protocol.toByteArray(replicate));
		return this;
	}

	public JobParams setDelay(Integer delay) {
		params.add(Keyword.DELAY.raw);
		params.add(Protocol.toByteArray(delay));
		return this;
	}

	public JobParams setRetry(Integer retry) {
		params.add(Keyword.RETRY.raw);
		params.add(Protocol.toByteArray(retry));
		return this;
	}

	public JobParams setTTL(Integer ttl) {
		params.add(Keyword.TTL.raw);
		params.add(Protocol.toByteArray(ttl));
		return this;
	}

	public JobParams setMaxlen(Integer maxlen) {
		params.add(Keyword.MAXLEN.raw);
		params.add(Protocol.toByteArray(maxlen));
		return this;
	}

	public JobParams setAsync(Boolean async) {
		params.add(Keyword.ASYNC.raw);
		return this;
	}

	public Collection<byte[]> getParams() {
		return Collections.unmodifiableCollection(params);
	}
	
}



