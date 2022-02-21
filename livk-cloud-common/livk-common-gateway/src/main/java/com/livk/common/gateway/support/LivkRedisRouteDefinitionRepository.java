package com.livk.common.gateway.support;

import com.livk.common.redis.support.LivkReactiveRedisTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionRepository;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.data.redis.core.ReactiveHashOperations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

/**
 * <p>
 * RedisRouteDefinitionWriter
 * </p>
 *
 * @author livk
 * @date 2021/11/2
 */
@Slf4j
public class LivkRedisRouteDefinitionRepository implements RouteDefinitionRepository {

	public static final String ROUTE_KEY = "RouteDefinition";

	private final ReactiveHashOperations<String, String, RouteDefinition> reactiveHashOperations;

	public LivkRedisRouteDefinitionRepository(LivkReactiveRedisTemplate livkReactiveRedisTemplate) {
		reactiveHashOperations = livkReactiveRedisTemplate.opsForHash();
	}

	/**
	 * @return Flux<RouteDefinition>
	 */
	@Override
	public Flux<RouteDefinition> getRouteDefinitions() {
		return reactiveHashOperations.entries(ROUTE_KEY).map(Map.Entry::getValue);
	}

	/**
	 * @param route {@link com.livk.common.gateway.domain.LivkRoute}
	 * @return Mono.empty()
	 */
	@Override
	public Mono<Void> save(Mono<RouteDefinition> route) {
		return route.flatMap(r -> this.reactiveHashOperations.put(ROUTE_KEY, r.getId(), r)
				.flatMap(success -> Boolean.TRUE.equals(success) ? Mono.empty() : Mono.defer(() -> Mono.error(
						new RuntimeException(String.format("Could not add route to redis repository: %s", r))))));
	}

	@Override
	public Mono<Void> delete(Mono<String> routeId) {
		return routeId.flatMap(id -> this.reactiveHashOperations.remove(ROUTE_KEY, id)
				.flatMap(success -> success != 0 ? Mono.empty() : Mono.defer(() -> Mono.error(new NotFoundException(
						String.format("Could not remove route from redis repository with id: %s", id))))));
	}

}
