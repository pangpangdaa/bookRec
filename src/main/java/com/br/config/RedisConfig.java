package com.br.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import redis.clients.jedis.JedisPoolConfig;

@Configuration
@EnableCaching
public class RedisConfig {
	
	@Bean(name="redisTemplate")
	public RedisTemplate initRedisTemplate(){
		JedisPoolConfig poolConfig=new JedisPoolConfig();
		poolConfig.setMaxIdle(50);
		poolConfig.setMaxTotal(100);
		poolConfig.setMaxWaitMillis(20000);
		JedisConnectionFactory connectionFactory=new JedisConnectionFactory(poolConfig);
		connectionFactory.setHostName("localhost");
		connectionFactory.setPort(6379);
		connectionFactory.afterPropertiesSet();
		RedisSerializer jdkSerializationRedisSerializer=new JdkSerializationRedisSerializer();
		RedisSerializer stringRedisSerializer=new StringRedisSerializer();
		RedisTemplate redisTemplate=new RedisTemplate();
		redisTemplate.setConnectionFactory(connectionFactory);
		redisTemplate.setDefaultSerializer(stringRedisSerializer);
		redisTemplate.setKeySerializer(stringRedisSerializer);
		redisTemplate.setValueSerializer(jdkSerializationRedisSerializer);
		redisTemplate.setHashKeySerializer(stringRedisSerializer);
		redisTemplate.setHashValueSerializer(jdkSerializationRedisSerializer);
		return redisTemplate;
	}
	
	@Bean(name="redisCacheManager")
	public CacheManager initRedisCacheManager(@Autowired RedisTemplate redisTemplate){
		RedisCacheManager cacheManager=new RedisCacheManager(redisTemplate);
		cacheManager.setDefaultExpiration(600);
		List<String> cacheNames=new ArrayList<String>();
		cacheNames.add("redisCacheManager");
		cacheManager.setCacheNames(cacheNames);
		return cacheManager;
	}

}
