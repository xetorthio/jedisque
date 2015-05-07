# Jedisque

Jedisque is a minimal java client for [Disque](http://github.com/antirez/disque "Disque").

Jedisque uses [Jedis](http://github.com/xetorthio/jedis "Jedis") as a redis client.

## How do I use it?

To use it just:

```xml
<dependency>
    <groupId>com.github.xetorthio</groupId>
    <artifactId>jedisque</artifactId>
    <version>x.y.z</version>
    <type>jar</type>
    <scope>compile</scope>
</dependency>
```

Please replace ```x.y.z``` version with one of the available versions.
    
```java
Jedisque q = new Jedisque(
			new URI("disque://192.168.0.1:7711"),
			new URI("disque://192.168.0.4:8822")
		);
String jobId = q.addJob("foo", "bar", 10000);
```

```java
Jedisque q = new Jedisque(
			new URI("disque://192.168.0.1:7711"),
			new URI("disque://192.168.0.4:8822")
		);
List<Job> jobs = q.getJob("foo", "foo2");
```

For more usage examples check the [tests](https://github.com/xetorthio/jedisque/blob/master/src/test/java/com/github/xetorthio/jedisque/JedisqueTest.java).

And you are done!
