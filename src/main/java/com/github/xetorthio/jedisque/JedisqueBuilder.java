package com.github.xetorthio.jedisque;

import redis.clients.jedis.Builder;
import redis.clients.jedis.BuilderFactory;
import redis.clients.util.SafeEncoder;

import java.util.ArrayList;
import java.util.List;

public class JedisqueBuilder extends BuilderFactory {

	public static final Builder<List<Job>> JOB_LIST = new Builder<List<Job>>() {
		@SuppressWarnings("unchecked")
		public List<Job> build(Object data) {
			if (null == data) {
				return null;
			}
			List<List<byte[]>> l = (List<List<byte[]>>) data;

			// Order matters that's why we're using a LL
			final List<Job> result = new ArrayList<Job>();

			for (List<byte[]> rawJob : l) {
				result.add(new Job(SafeEncoder.encode(rawJob.get(0)), SafeEncoder.encode(rawJob.get(1)),
						rawJob.get(2)));
			}

			return result;
		}

	};

	public static final Builder<JobInfo> JOB_SHOW = new Builder<JobInfo>() {
		@SuppressWarnings("unchecked")
		public JobInfo build(Object data) {
			if (null == data) {
				return null;
			}
			List<byte[]> showRaw = (List<byte[]>) data;
			
			return new JobInfo(SafeEncoder.encode(showRaw.get(1)), 
					SafeEncoder.encode(showRaw.get(3)),
					SafeEncoder.encode(showRaw.get(5)), 
					BuilderFactory.LONG.build(showRaw.get(7)), 
					BuilderFactory.LONG.build(showRaw.get(9)),
					BuilderFactory.LONG.build(showRaw.get(11)),
					BuilderFactory.LONG.build(showRaw.get(13)), 
					BuilderFactory.LONG.build(showRaw.get(15)), 
					BuilderFactory.STRING_LIST.build(showRaw.get(17)), 
					BuilderFactory.STRING_LIST.build(showRaw.get(19)),
					BuilderFactory.LONG.build(showRaw.get(21)),
					BuilderFactory.LONG.build(showRaw.get(23)), 
					SafeEncoder.encode(showRaw.get(25)));
		}

	};

}
