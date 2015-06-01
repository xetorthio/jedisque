package com.github.xetorthio.jedisque;

import redis.clients.util.SafeEncoder;

import java.util.Arrays;

public class Job {

	private byte[] id;
	private byte[] queueName;
	private byte[] body;

	public Job() {
	}

	public Job(byte[] queueName, byte[] id, byte[] body) {
		super();
		this.id = id;
		this.queueName = queueName;
		this.body = body;
	}

	public String getId() {
		return SafeEncoder.encode(id);
	}

	public byte[] getIdAsBytes() {
		return id;
	}

	public void setId(byte[] id) {
		this.id = id;
	}

	public String getQueueName() {
		return SafeEncoder.encode(queueName);
	}

	public byte[] getQueueNameAsBytes() {
		return this.queueName;
	}

	public void setQueueName(byte[] queueName) {
		this.queueName = queueName;
	}

	public String getBody() {
		return SafeEncoder.encode(body);
	}

	public byte[] getBodyAsBytes() {
		return body;
	}

	public String getStringBody() {
		return SafeEncoder.encode(body);
	}

	public void setBody(byte[] body) {
		this.body = body;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((body == null) ? 0 : body.hashCode());
		result = prime * result
				+ ((queueName == null) ? 0 : queueName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {

		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Job other = (Job) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!Arrays.equals(id, other.id))
			return false;
		if (body == null) {
			if (other.body != null)
				return false;
		} else if (!Arrays.equals(body, other.body))
			return false;
		if (queueName == null) {
			if (other.queueName != null)
				return false;
		} else if (!Arrays.equals(queueName, other.queueName))
			return false;
		return true;
	}

}
