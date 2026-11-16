package com.gpuduck.hubduck

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class HubduckApplication

fun main(args: Array<String>) {
	runApplication<HubduckApplication>(*args)
}
