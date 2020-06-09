package org.infobourg.betterrss

import org.infobourg.betterrss.services.RssFluxService
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication


@SpringBootApplication
open class BetterRssApplication (private val rssFluxService: RssFluxService) : ApplicationRunner{
    companion object {

        @JvmStatic
        fun main(args: Array<String>) {
            SpringApplication.run(BetterRssApplication::class.java, *args)
        }
    }
    override fun run(args: ApplicationArguments?) {
        for(rss in this.rssFluxService.findAll()) {
            println(rss);
        }

        for(rss in this.rssFluxService.findByIdChannel("12354644458")) {
            println(rss);
        }
    }
}