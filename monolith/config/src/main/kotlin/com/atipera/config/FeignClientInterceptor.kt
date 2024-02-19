package com.atipera.config

import feign.RequestInterceptor
import feign.RequestTemplate

class FeignClientInterceptor(private val gitHubApiToken: String) : RequestInterceptor {
    override fun apply(requestTemplate: RequestTemplate) {
        requestTemplate.header("Authorization", "Bearer $gitHubApiToken")
        requestTemplate.header("GitHub-Api-Version", "2022-11-28")
        requestTemplate.header("Content-Type", "application/json")
    }
}