package com.github.xetorthio.jedisque;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;
import java.util.UUID;

import org.junit.Ignore;
import org.junit.Test;

public class JedisqueTest {
	private static String getQueueName() {
		return UUID.randomUUID().toString();
	}

	@Test
	public void addJob() {
		try (Jedisque q = new Jedisque()) {
			String jobId = q.addJob(getQueueName(), "message", 10);
			assertNotNull(jobId);
		}
	}

	@Test
	public void getJob() {
		try (Jedisque q = new Jedisque()) {
			String queue = getQueueName();
			String jobId = q.addJob(queue, "message", 10);
			List<Job> jobs = q.getJob(queue);
			Job job = jobs.get(0);
			assertEquals(jobId, job.getId());
			assertEquals("message", job.getBody());
			assertEquals(queue, job.getQueueName());
		}
	}

	@Test
	public void ackJob() {
		try (Jedisque q = new Jedisque()) {
			String jobId = q.addJob(getQueueName(), "message", 10);
			Long count = q.ackjob(jobId);
			assertEquals(count.longValue(), 1);
			assertEquals(q.qlen("somequeue").longValue(), 0);
		}
	}

	@Test
	public void fastAck() {
		try (Jedisque q = new Jedisque()) {
			String jobId = q.addJob("fastack", "message", 10);
			Long count = q.fastack(jobId);
			assertEquals(count.longValue(), 1);
			assertEquals(q.qlen("fastack").longValue(), 0);
		}
	}

	@Test
	public void info() {
		try (Jedisque q = new Jedisque()) {
			String info = q.info();
			assertNotNull(info);
			info = q.info("server");
			assertNotNull(info);
		}
	}

	@Test
	@Ignore(value = "pending")
	public void hello() {

	}

	@Test
	public void qlen() {
		try (Jedisque q = new Jedisque()) {
			String queue = getQueueName();
			q.addJob(queue, "testJob", 10);
			q.addJob(queue, "testJob2", 10);
			Long qlen = q.qlen(queue);
			assertEquals(qlen.longValue(), 2);
		}
	}

	@Test
	@Ignore(value = "pending (not yet implemented)")
	public void qstat() {
	}

	@Test
	public void qpeek() {
		try (Jedisque q = new Jedisque()) {
			String queue = getQueueName();
			q.addJob(queue, "testJob", 10);
			q.addJob(queue, "testJob2", 10);
			List<Job> jobs = q.qpeek(queue, 2);
			Job job = jobs.get(0);
			assertEquals(job.getBody(), "testJob");
			job = jobs.get(1);
			assertEquals(job.getBody(), "testJob2");
			assertEquals(q.qlen(queue).longValue(), 2);
		}
	}

	@Test
	public void qpeekEmpty() {
		try (Jedisque q = new Jedisque()) {
			List<Job> jobs = q.qpeek(getQueueName(), 2);
			assertEquals(jobs.size(), 0);
		}
	}

	@Test
	public void qpeekInverse() {
		try (Jedisque q = new Jedisque()) {
			String queue = getQueueName();
			q.addJob(queue, "testJob", 10);
			q.addJob(queue, "testJob2", 10);
			List<Job> jobs = q.qpeek(queue, -2);
			Job job = jobs.get(0);
			assertEquals(job.getBody(), "testJob2");
			job = jobs.get(1);
			assertEquals(job.getBody(), "testJob");
			assertEquals(q.qlen(queue).longValue(), 2);
		}
	}

	@Test
	public void enqueue() {
		try (Jedisque q = new Jedisque()) {
			String queue = getQueueName();
			String jobId = q.addJob(queue, "testJob", 10);
			q.dequeue(jobId);
			assertEquals(q.qlen(queue).longValue(), 0);
			Long count = q.enqueue(jobId);
			assertEquals(count.longValue(), 1);
			assertEquals(q.qlen(queue).longValue(), 1);
		}
	}

	@Test
	public void dequeue() throws InterruptedException {
		try (Jedisque q = new Jedisque()) {
			String queue = getQueueName();
			String jobId = q.addJob(queue, "testJob", 10);
			System.out.println(jobId);
			System.out.println(queue);
			Long count = q.dequeue(jobId);
			assertEquals(count.longValue(), 1);
		}
	}

	@Test
	public void delJob() {
		try (Jedisque q = new Jedisque()) {
			String queue = getQueueName();
			String jobId = q.addJob(queue, "testJob", 10);
			Long count = q.delJob(jobId);
			assertEquals(count.longValue(), 1);
			assertEquals(q.qlen(queue).longValue(), 0);
		}
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
