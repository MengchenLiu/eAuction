package com.eAuction.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class RedisConfig {
	/**
	//@Bean
	public RedisTemplate<String , Object> redisTemplate(RedisConnectionFactory redisConnectionFactory){
		RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
		template.setConnectionFactory(redisConnectionFactory);
		template.setKeySerializer(new StringRedisSerializer());
		template.setValueSerializer(new JdkSerializationRedisSerializer());
		return template;
	}
    **/
}
