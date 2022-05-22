package com.example.practicejpa.config;


import org.ehcache.config.CacheConfiguration;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.EntryUnit;
import org.ehcache.config.units.MemoryUnit;
import org.ehcache.jsr107.Eh107Configuration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.spi.CachingProvider;
import java.time.Duration;

@EnableCaching
@Configuration
public class CashConfig {
	
	@Bean(name = "SSECahcheManager")
	public CacheManager cacheManager(){
		
		CachingProvider cachingProvider = Caching.getCachingProvider();
		CacheManager cacheManager = cachingProvider.getCacheManager();
		
		CacheConfiguration<String, Object> cacheConfig = CacheConfigurationBuilder
				.newCacheConfigurationBuilder(String.class, Object.class,
						ResourcePoolsBuilder.newResourcePoolsBuilder()
						                    .heap(1000, EntryUnit.ENTRIES) // 최대 1000개까지 객체를 저장할 수 있도록 선언하는 메소드
						                    .offheap(100, MemoryUnit.MB)    // 최대 데이터량이 100MB까지 허용하도록 선언하는 메소드
						// .disk(100, MemoryUnit.MB, true)
				).withSizeOfMaxObjectSize(1, MemoryUnit.MB) // 한 객체의 최대 사이즈
				.withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(6000)))  // 첫 생성 후 600초까지 유지
				.withExpiry(ExpiryPolicyBuilder.timeToIdleExpiration(Duration.ofSeconds(6000))).build();// 갱신되면 600초 더 유지
		
		javax.cache.configuration.Configuration<String, Object> configuration = Eh107Configuration.fromEhcacheCacheConfiguration(cacheConfig);
		
		cacheManager.createCache("sse", configuration);
		
		return cacheManager;
	}
	
	
	
	
	// @Bean
	// public CacheManager cacheManager(){
	// 	// ConcurrentMapCacheManager  가장 기본적인 캐시
	// 	// 단순히 맵형태로 부가적인 기능이 없음, 대신 속도는 빠름
	// 	// 용량제한이나 다양한 저장방식지원, 다중 서버분산과 같은 기능이 없음
	// 	// 실제로 위에 @EnableCaching선언하면 default로 설정되는 매니저임
	// 	SimpleCacheManager simpleCacheManager = new SimpleCacheManager();
	// 	simpleCacheManager.setCaches(List.of(new ConcurrentMapCache("sse")));
	// 	return simpleCacheManager;
	// }

}
