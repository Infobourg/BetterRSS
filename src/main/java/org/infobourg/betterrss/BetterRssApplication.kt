package org.infobourg.betterrss

import org.infobourg.betterrss.services.RssFeedService
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.web.servlet.ServletComponentScan


@SpringBootApplication
open class BetterRssApplication (private val rssFeedService: RssFeedService) : ApplicationRunner {
    companion object {

        @JvmStatic
        fun main(args: Array<String>) {
            SpringApplication.run(BetterRssApplication::class.java, *args)
        }
    }
    override fun run(args: ApplicationArguments?) {
        for(rss in this.rssFeedService.findAll()) {
            println(rss);
        }

        for(rss in this.rssFeedService.findByIdChannel("12354644458")) {
            println(rss);
        }
    }
}