package com.atipera.config

import org.springframework.context.annotation.Bean
import org.springframework.web.reactive.function.client.WebClient
import reactivefeign.client.ReactiveHttpRequest
import reactivefeign.client.ReactiveHttpRequestInterceptor
import reactor.core.publisher.Mono

class FeignClientInterceptor(private val gitHubApiToken: String) : ReactiveHttpRequestInterceptor {

    override fun apply(t: ReactiveHttpRequest?): Mono<ReactiveHttpRequest> {
        t!!.headers().put("Authorization", listOf("Bearer $gitHubApiToken"))
        t!!.headers().put("GitHub-Api-Version", listOf("2022-11-28"))
        t!!.headers().put("Content-Type", listOf("application/json"))
        return Mono.just(t)
    }

    @Bean
    fun webClientBuilder(): WebClient.Builder {
        return WebClient.builder()
    }
}
