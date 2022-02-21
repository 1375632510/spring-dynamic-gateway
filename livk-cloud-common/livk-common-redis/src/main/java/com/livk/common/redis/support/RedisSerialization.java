package com.livk.common.redis.support;

import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.lang.NonNull;

/**
 * <p>
 * LivkRedisSerializationContext
 * </p>
 *
 * @author livk
 * @date 2022/2/21
 */
public interface RedisSerialization<V> extends RedisSerializationContext<String, V> {

	@NonNull
	@Override
	default SerializationPair<String> getKeySerializationPair() {
		return RedisSerializationContext.SerializationPair.fromSerializer(RedisSerializer.string());
	}

	@NonNull
	@SuppressWarnings("unchecked")
	@Override
	default SerializationPair<String> getHashKeySerializationPair() {
		return RedisSerializationContext.SerializationPair.fromSerializer(RedisSerializer.string());
	}

	@NonNull
	@Override
	default SerializationPair<String> getStringSerializationPair() {
		return RedisSerializationContext.SerializationPair.fromSerializer(RedisSerializer.string());
	}

	static <T> Jackson2RedisSerializationContext<T> json(Class<T> targetClass) {
		return new Jackson2RedisSerializationContext<>(targetClass);
	}

	static Jackson2RedisSerializationContext<Object> json() {
		return json(Object.class);
	}

	static JdkRedisSerializationContext java() {
		return new JdkRedisSerializationContext();
	}

}
