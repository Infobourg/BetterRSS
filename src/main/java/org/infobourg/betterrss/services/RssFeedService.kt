package org.infobourg.betterrss.services

import org.infobourg.betterrss.models.RssFeed
import org.infobourg.betterrss.repositories.RssFeedRepository
import org.springframework.stereotype.Service


@Service
class RssFeedService(var rssFeedRepository: RssFeedRepository){
    fun findAll(): List<RssFeed> = rssFeedRepository.findAll()
    fun findByIdWorkspace(idWorkspace: String): List<RssFeed> = rssFeedRepository.findByIdWorkspace(idWorkspace)
    fun findByIdChannel(idChannel: String): List<RssFeed> = rssFeedRepository.findByIdChannel(idChannel)
    fun findByIdThread(idThread: String): List<RssFeed> = rssFeedRepository.findByIdThread(idThread)
    fun save(feed: RssFeed): RssFeed = rssFeedRepository.save(feed)
}