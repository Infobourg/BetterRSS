package org.infobourg.betterrss.repositories

import org.infobourg.betterrss.models.RssFeed
import org.springframework.data.mongodb.repository.MongoRepository

interface RssFeedRepository : MongoRepository<RssFeed, String> {
    fun findByIdChannel(idChannel: String): List<RssFeed>
    fun findByIdWorkspace(idWorkspace: String): List<RssFeed>
    fun findByIdMessage(idMessage: String): List<RssFeed>
    fun findByIdThread(idThread: String): List<RssFeed>
}