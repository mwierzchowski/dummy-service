package com.github.mwierzchowski.dummy.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.mwierzchowski.dummy.core.DummyLogger;
import com.github.mwierzchowski.dummy.core.DummyTask;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;

@Configuration
public class RedisConfig {
    @Bean
    RedisTemplate<String, Object> jsonRedisTemplate(RedisConnectionFactory rdc, ObjectMapper om) {
        var redisTemplate = new RedisTemplate<String, Object>();
        redisTemplate.setConnectionFactory(rdc);
        redisTemplate.setKeySerializer(RedisSerializer.string());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer(om));
        return redisTemplate;
    }

    @Bean
    MessageListenerAdapter dummyTaskListener(DummyLogger dummyLogger, ObjectMapper om) {
        var serializer = new Jackson2JsonRedisSerializer<>(DummyTask.class);
        serializer.setObjectMapper(om);
        var adapter = new MessageListenerAdapter();
        adapter.setDelegate(dummyLogger);
        adapter.setSerializer(serializer);
        return adapter;
    }

    @Bean
    RedisMessageListenerContainer messageListenerContainer(RedisConnectionFactory rcf, MessageListenerAdapter mla,
                                                           @Value("${publisher.channel}") String channel) {
        var container = new RedisMessageListenerContainer();
        container.setConnectionFactory(rcf);
        container.addMessageListener(mla, new ChannelTopic(channel));
        return container;
    }
}
