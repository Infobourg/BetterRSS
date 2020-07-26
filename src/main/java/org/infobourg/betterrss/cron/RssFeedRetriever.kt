package org.infobourg.betterrss.cron

import com.rometools.rome.feed.synd.SyndEntry
import com.slack.api.Slack
import kotlinx.coroutines.runBlocking
import org.infobourg.betterrss.add.RssParser
import org.infobourg.betterrss.services.RssFeedService
import org.infobourg.betterrss.slack.*
import org.springframework.core.env.Environment
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.util.*


@Service
@EnableScheduling
class RssFeedRetriever(private val rssParser: RssParser, private val rssService: RssFeedService, private val env: Environment) {

    private val slackToken: String = env.getProperty("SLACK_BOT_TOKEN") ?: ""

    @Scheduled(cron = "*/10 * * * * *")
    fun currentTime() {
        val rssFeeds = rssService.findAll();
        for (rssFeed in rssFeeds) {
            val rssFeedResult = runBlocking { rssParser.parseRssFeed(rssFeed.link) }
                    ?: return
            val imageUrlToShow = if (rssFeedResult.image != null) rssFeedResult.image.url else "https://pbs.twimg.com/profile_images/625633822235693056/lNGUneLX_400x400.jpg"
            val entriesToShow = mutableListOf<SyndEntry>()
            for (entry in rssFeedResult.entries) {
                if (entry.publishedDate.after(rssFeed.lastUpdate)) {
                    entriesToShow.add(entry)
                }
                // todo: check if always sort by date. if the case, we can uncomment the else
                /*
                else {
                    break;
                }
                */
            }
            rssService.save(rssFeed.copy(lastUpdate = Date()));
            if(entriesToShow.isEmpty()) return
            val response = Slack.getInstance().methods(slackToken).chatPostMessage { req ->
                req.channel(rssFeed.idChannel).threadTs(rssFeed.idThread).blocks(
                        block {
                            section {
                                text = markdown { text = "*${rssFeedResult.title}*\n" + entriesToShow.joinToString("\n"){entry ->"<${entry.link}|${entry.title}>" }}
                                accessory = image { altText = "Picture of the news"; imageUrl = imageUrlToShow }
                            }
                        }
                )
            }
            println(response.message)
            println(response.error)
        }
    }


}