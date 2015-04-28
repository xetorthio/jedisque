package com.github.xetorthio.jedisque;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

public class JedisqueTest {
	@Test
	public void addJob() {
		try (Jedisque q = new Jedisque()) {
			String jobId = q.addJob("queuename", "message", 10);
			assertNotNull(jobId);
		}
	}

	@Test
	public void getJob() {
		try (Jedisque q = new Jedisque()) {
			String jobId = q.addJob("somequeue", "message", 10);
			List<Job> jobs = q.getJob("somequeue");
			Job job = jobs.get(0);
			assertEquals(jobId, job.getId());
			assertEquals("message", job.getBody());
			assertEquals("somequeue", job.getQueueName());
		}
	}

	@Test
	public void ackJob() {
		try (Jedisque q = new Jedisque()) {
			String jobId = q.addJob("somequeue", "message", 10);
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
			q.addJob("test", "testJob", 10);
			q.addJob("test", "testJob2", 10);
			Long qlen = q.qlen("test");
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
			q.addJob("peek", "testJob", 10);
			q.addJob("peek", "testJob2", 10);
			List<Job> jobs = q.qpeek("peek", 2);
			Job job = jobs.get(0);
			assertEquals(job.getBody(), "testJob");
			job = jobs.get(1);
			assertEquals(job.getBody(), "testJob2");
			assertEquals(q.qlen("peek").longValue(), 2);
		}
	}
	
	@Test
	public void qpeekEmpty() {
		try (Jedisque q = new Jedisque()) {
			List<Job> jobs = q.qpeek("empty", 2);
			assertEquals(jobs.size(), 0);
		}
	}
	
	@Test
	public void qpeekInverse() {
		try (Jedisque q = new Jedisque()) {
			q.addJob("peekInv", "testJob", 10);
			q.addJob("peekInv", "testJob2", 10);
			List<Job> jobs = q.qpeek("peekInv", -2);
			Job job = jobs.get(0);
			assertEquals(job.getBody(), "testJob2");
			job = jobs.get(1);
			assertEquals(job.getBody(), "testJob");
			assertEquals(q.qlen("peekInv").longValue(), 2);
		}
	}

	@Test
	public void enqueue() {
		try (Jedisque q = new Jedisque()) {
			String jobId = q.addJob("enqueue", "testJob", 10);
			q.dequeue(jobId);
			assertEquals(q.qlen("enqueue").longValue(), 0);
			Long count = q.enqueue(jobId);
			assertEquals(count.longValue(), 1);
			assertEquals(q.qlen("enqueue").longValue(), 1);
		}
	}

	@Test
	public void dequeue() {
		try (Jedisque q = new Jedisque()) {
			String jobId = q.addJob("dequeue", "testJob", 10);
			Long count = q.dequeue(jobId);
			assertEquals(count.longValue(), 1);
			assertEquals(q.qlen("dequeue").longValue(), 0);
		}
	}

	@Test
	public void delJob() {
		try (Jedisque q = new Jedisque()) {
			String jobId = q.addJob("del", "testJob", 10);
			Long count = q.delJob(jobId);
			assertEquals(count.longValue(), 1);
			assertEquals(q.qlen("del").longValue(), 0);
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
