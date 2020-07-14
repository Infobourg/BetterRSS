package org.infobourg.betterrss.cron

import kotlinx.coroutines.runBlocking
import org.infobourg.betterrss.add.RssParser
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service


@Service
@EnableScheduling
class RssFeedRetriever(private val rssParser: RssParser) {
    private val log: Logger = LoggerFactory.getLogger(RssFeedRetriever::class.java)

    @Scheduled(cron = "*/10 * * * * *")
    fun currentTime() {
        val rssFeed = runBlocking { rssParser.parseRssFeed("https://stackoverflow.com/feeds/tag?tagnames=rome") }
                ?: return
        log.info("Title      = {}", rssFeed.title)
        log.info("Entries      = {}", rssFeed.entries.joinToString(", "){entry -> entry.title})
    }


}