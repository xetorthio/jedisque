package com.github.xetorthio.jedisque;

import static org.junit.Assert.assertNotNull;

import org.junit.Ignore;
import org.junit.Test;

public class JedisqueTest {
	@Test
	public void addJob() {
		try (Jedisque q = new Jedisque()) {
			String jobId = q.addJob("queuename", "jobname", 10);
			assertNotNull(jobId);
		}
	}

	@Test
	@Ignore(value = "pending")
	public void getJob() {
	}

	@Test
	@Ignore(value = "pending")
	public void ackJob() {
	}

	@Test
	@Ignore(value = "pending")
	public void fastAck() {
	}

	@Test
	@Ignore(value = "pending")
	public void info() {
	}

	@Test
	@Ignore(value = "pending")
	public void hello() {
	}

	@Test
	@Ignore(value = "pending")
	public void qlen() {
	}

	@Test
	@Ignore(value = "pending")
	public void qstat() {
	}

	@Test
	@Ignore(value = "pending")
	public void qpeek() {
	}

	@Test
	@Ignore(value = "pending")
	public void enqueue() {
	}

	@Test
	@Ignore(value = "pending")
	public void dequeue() {
	}

	@Test
	@Ignore(value = "pending")
	public void delJob() {
	}

	@Test
	@Ignore(value = "pending")
	public void show() {
	}

	@Test
	@Ignore(value = "pending")
	public void scan() {
	}
}
