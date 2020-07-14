package org.infobourg.betterrss.add

import com.rometools.rome.feed.synd.SyndFeed
import com.rometools.rome.io.FeedException
import com.rometools.rome.io.SyndFeedInput
import com.rometools.rome.io.XmlReader
import io.ktor.client.HttpClient
import io.ktor.client.call.receive
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Component
import java.io.IOException
import java.io.InputStream

@Component
open class RssParser(
        private val client: HttpClient = HttpClient(),
        private val input: SyndFeedInput = SyndFeedInput()
) {
    suspend fun isRssFeed(url: String): Boolean {
        return parseRssFeed(url) != null
    }

    suspend fun parseRssFeed(url: String): SyndFeed? = withContext(Dispatchers.IO) {
        try {
            val body = client.get<HttpResponse>(url).receive<InputStream>()
            return@withContext input.build(XmlReader(body))
        } catch (e: IOException) {
            return@withContext null
        } catch (e: IllegalArgumentException) {
            return@withContext null
        } catch (e: FeedException) {
            return@withContext null
        }
    }

}