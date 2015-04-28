[![Build Status](https://travis-ci.org/xetorthio/jedisque.png?branch=master)](https://travis-ci.org/xetorthio/jedisque)

# Jedisque

Jedisque is a minimal java client for [Disque](http://github.com/antirez/disque "Disque").

Jedisque uses [Jedis](http://github.com/xetorthio/jedis "Jedis") as a redis client.

## How do I use it?

To use it just:
    
```java
Jedisque q = new Jedisque(
			new URI("disque://192.168.0.1:7711"),
			new URI("disque://192.168.0.4:8822")
		);
Job j = new Job("foo", "bar", 10000);
String jobId = q.addJob(j);
```

```java
Jedisque q = new Jedisque(
			new URI("disque://192.168.0.1:7711"),
			new URI("disque://192.168.0.4:8822")
		);
List<Job> jobs = q.getJob("foo", "foo2");
```

For more usage examples check the tests.

And you are done!
