package org.infobourg.betterrss.cron

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.text.SimpleDateFormat
import java.util.*


@Service
@EnableScheduling
class RssFeedRetriever {
    private val log: Logger = LoggerFactory.getLogger(RssFeedRetriever::class.java)
    private val dateFormat = SimpleDateFormat("HH:mm:ss")

    @Scheduled(cron = "*/5 * * * * *")
    fun currentTime() {
        log.info("Current Time      = {}", dateFormat.format(Date()))
        println("test")
    }
}