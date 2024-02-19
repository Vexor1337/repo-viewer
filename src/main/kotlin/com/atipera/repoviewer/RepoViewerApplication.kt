package com.atipera.repoviewer

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class RepoViewerApplication

fun main(args: Array<String>) {
	runApplication<RepoViewerApplication>(*args)
}
