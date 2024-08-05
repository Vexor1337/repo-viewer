package com.atipera.config

import com.atipera.app.infrastructure.client.GitHubFeignClient
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import reactivefeign.webclient.WebReactiveFeign

@Configuration
class GitHubFeignClientConfig {
    @Bean
    fun createGitHubFeignClient(@Value("\${github.api.url}") url: String, @Value("\${github.api.token}") riotApiToken: String): GitHubFeignClient {
        return WebReactiveFeign.builder<GitHubFeignClient>()
            .addRequestInterceptor(FeignClientInterceptor(riotApiToken))
            //.decoder(GsonDecoder())
            //.encoder(GsonEncoder())
            .target(GitHubFeignClient::class.java, url)
    }
}