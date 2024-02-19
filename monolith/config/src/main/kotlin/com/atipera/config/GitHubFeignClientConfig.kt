package com.atipera.config

import com.atipera.app.infrastructure.client.GitHubFeignClient
import feign.Feign
import feign.gson.GsonDecoder
import feign.gson.GsonEncoder
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class GitHubFeignClientConfig {
    @Bean
    fun createGitHubFeignClient(@Value("\${github.api.url}") url: String, @Value("\${github.api.token}") riotApiToken: String): GitHubFeignClient {
        return Feign.builder()
            .requestInterceptor(FeignClientInterceptor(riotApiToken))
            .decoder(GsonDecoder())
            .encoder(GsonEncoder())
            .target(GitHubFeignClient::class.java, url)
    }

}