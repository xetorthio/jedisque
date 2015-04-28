package com.github.xetorthio.jedisque;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;
import java.util.UUID;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class JedisqueTest {
	private static String getQueueName() {
		return UUID.randomUUID().toString();
	}

	static Jedisque q;

	@Before
	public void setUp() throws Exception {
		q = new Jedisque();
	}

	@After
	public void tearDown() throws Exception {
		q.close();
	}

	@Test
	public void addJob() {
		String jobId = q.addJob(getQueueName(), "message", 10);
		assertNotNull(jobId);
	}

	@Test
	public void getJob() {
		String queue = getQueueName();
		String jobId = q.addJob(queue, "message", 10);
		List<Job> jobs = q.getJob(queue);
		Job job = jobs.get(0);
		assertEquals(jobId, job.getId());
		assertEquals("message", job.getBody());
		assertEquals(queue, job.getQueueName());
	}

	@Test
	public void ackJob() {
		String jobId = q.addJob(getQueueName(), "message", 10);
		Long count = q.ackjob(jobId);
		assertEquals(count.longValue(), 1);
	}

	@Test
	public void fastAck() {
		String jobId = q.addJob("fastack", "message", 10);
		Long count = q.fastack(jobId);
		assertEquals(count.longValue(), 1);
	}

	@Test
	public void info() {
		String info = q.info();
		assertNotNull(info);
		info = q.info("server");
		assertNotNull(info);
	}

	@Test
	@Ignore(value = "pending")
	public void hello() {

	}

	@Test
	public void qlen() {
		String queue = getQueueName();
		Long qlen = q.qlen(queue);
		assertEquals(qlen.longValue(), 0);
	}

	@Test
	@Ignore(value = "pending (not yet implemented)")
	public void qstat() {
	}

	@Test
	public void qpeek() {
		// We're testing also the response parsing here
		String queue = getQueueName();
		q.addJob(queue, "testJob", 10);
		q.addJob(queue, "testJob2", 10);
		List<Job> jobs = q.qpeek(queue, 2);
		Job job = jobs.get(0);
		assertEquals(job.getBody(), "testJob");
		job = jobs.get(1);
		assertEquals(job.getBody(), "testJob2");
	}

	@Test
	public void qpeekEmpty() {
		List<Job> jobs = q.qpeek(getQueueName(), 2);
		assertEquals(jobs.size(), 0);
	}

	@Test
	public void qpeekInverse() {
		String queue = getQueueName();
		List<Job> jobs = q.qpeek(queue, -2);
		assertEquals(jobs.size(), 0);
	}

	@Test
	public void enqueue() {
			String queue = getQueueName();
			String jobId = q.addJob(queue, "testJob", 10);
			Long count = q.enqueue(jobId);
			assertEquals(count.longValue(), 0);
	}

	@Test
	public void dequeue() throws InterruptedException {
			String queue = getQueueName();
			String jobId = q.addJob(queue, "testJob", 10);
			Long count = q.dequeue(jobId);
			assertEquals(count.longValue(), 1);
	}

	@Test
	public void delJob() {
			String queue = getQueueName();
			String jobId = q.addJob(queue, "testJob", 10);
			Long count = q.delJob(jobId);
			assertEquals(count.longValue(), 1);
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
