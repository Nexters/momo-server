package com.nexters.momo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MomoServerApplication

fun main(args: Array<String>) {
    runApplication<MomoServerApplication>(*args)
}
