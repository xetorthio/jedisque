package com.github.xetorthio.jedisque;

import java.util.LinkedList;
import java.util.List;

import redis.clients.jedis.Builder;
import redis.clients.jedis.BuilderFactory;
import redis.clients.util.SafeEncoder;

public class JedisqueBuilder extends BuilderFactory {
	
	public static final Builder<List<Job>> JOB_LIST = new Builder<List<Job>>() {
	    @SuppressWarnings("unchecked")
	    public List<Job> build(Object data) {
	      if (null == data) {
	        return null;
	      }
	      List<List<byte[]>> l =  (List<List<byte[]>>) data;
	      
	      // Order matters that's why we're using a LL
	      final List<Job> result = new LinkedList<Job>();
	      
	      for (List<byte[]> rawJob : l) {
			result.add(new Job(SafeEncoder.encode(rawJob.get(0)), SafeEncoder.encode(rawJob.get(1)), SafeEncoder.encode(rawJob.get(2))));
	      }
	      
	      return result;
	    }

	    public String toString() {
	      return "List<String>";
	    }

	  };
	  
	  public static final Builder<List<Job>> JOB_PEEK = new Builder<List<Job>>() {
		    @SuppressWarnings("unchecked")
		    public List<Job> build(Object data) {
		      if (null == data) {
		        return null;
		      }
		      List<List<byte[]>> l =  (List<List<byte[]>>) data;
		      
		      // Order matters that's why we're using a LL
		      final List<Job> result = new LinkedList<Job>();
		      
		      for (List<byte[]> rawJob : l) {
				result.add(new Job(null, SafeEncoder.encode(rawJob.get(0)), SafeEncoder.encode(rawJob.get(1))));
		      }
		      
		      return result;
		    }

		    public String toString() {
		      return "List<String>";
		    }

		  };


}
